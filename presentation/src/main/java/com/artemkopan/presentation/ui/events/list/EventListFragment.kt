package com.artemkopan.presentation.ui.events.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.artemkopan.core.entity.CategoryEntity
import com.artemkopan.core.tools.UiState
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseFragment
import com.artemkopan.presentation.base.Injectable
import com.artemkopan.presentation.base.recycler.RecyclerStateManager
import com.artemkopan.presentation.ui.events.EventsComponent
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.fragment_event_list.*
import javax.inject.Inject

class EventListFragment : BaseFragment<EventListViewModel>(), Injectable {

    @Inject
    lateinit var adapter: EventsGroupAdapter

    private val recyclerState by lazy(LazyThreadSafetyMode.NONE) { RecyclerStateManager() }

    override fun inject(appProvider: ApplicationProvider) {
        EventsComponent.Initializer.init(appProvider).inject(this)
    }

    override fun getContentView(): Int = R.layout.fragment_event_list

    override fun getViewModelClass(): Class<EventListViewModel> = EventListViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            adapter.eventsBundle.putAll(it)
            recyclerState.restoreState(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsGroupRecyclerView.adapter = adapter
        eventsGroupRecyclerView.layoutManager = LinearLayoutManager(context)

        activity!!.toolbar.post {
            eventsGroupRecyclerView.addItemDecoration(EventsListItemDecoration(activity!!.toolbar.height, false))
        }

        adapter.setClickEvent { viewId, pos, item ->
            //            val args = Bundle().apply { putString("photo", item.thumbnail) }
//            NavHostFragment.findNavController(this).navigate(R.id.actionEventDetail, args)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeCategories()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(adapter.eventsBundle)
        adapter.eventsBundle.clear()
        recyclerState.saveState(eventsGroupRecyclerView, outState)
    }

    override fun onDestroyView() {
        eventsGroupRecyclerView.adapter = null
        super.onDestroyView()
    }

    private fun subscribeCategories() {
        viewModel.observeCategories()
                .subscribe {
                    //todo implement categories loading
                    when {
                        it.isError -> {
                            shoeError(it)
                        }
                        it.isSuccess -> {
                            it.data!!.let {
                                restoreRecyclerStateAndAnimate()
                                adapter.submitList(it)
                                subscribeEvents(it)
                            }
                        }
                    }
                }
                .addTo(destroyViewDisposable)
    }


    private fun subscribeEvents(categories: List<CategoryEntity>) {
        categories.forEach { (id) ->
            viewModel.observeEvents(id)
                    .subscribe {
                        adapter.showEventsLoading(id, it.isLoading)
                        when {
                            it.isError -> {
                                shoeError(it)
                            }
                            it.isSuccess -> {
                                eventsGroupRecyclerView.post { adapter.submitEvents(id, it.data!!) }
                            }
                        }
                    }
                    .addTo(destroyViewDisposable)
        }
    }

    private fun shoeError(it: UiState<*>) {
        Toast.makeText(context, it.throwable?.message ?: "", Toast.LENGTH_LONG)
                .show()
    }

    private fun restoreRecyclerStateAndAnimate() {
        if (!recyclerState.hasState() && adapter.getRealSize() == 0) {
            TransitionManager.beginDelayedTransition(
                    eventsGroupRecyclerView,
                    TransitionSet().apply {
                        addTransition(Fade().setDuration(100))
                        addTransition(Slide(Gravity.BOTTOM).setDuration(200))
                    }
            )
        } else if (recyclerState.hasState()) {
            recyclerState.applyState(eventsGroupRecyclerView)
        }
    }
}
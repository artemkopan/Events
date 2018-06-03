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
import com.artemkopan.core.tools.DataUiState
import com.artemkopan.core.tools.ErrorUiState
import com.artemkopan.core.tools.LoadingUiState
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseFragment
import com.artemkopan.presentation.base.Injectable
import com.artemkopan.presentation.ui.events.EventsComponent
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_event_list.*
import javax.inject.Inject

class EventListFragment : BaseFragment<EventListViewModel>(), Injectable {

    @Inject
    lateinit var adapter: EventsGroupAdapter

    override fun inject(appProvider: ApplicationProvider) {
        EventsComponent.Initializer.init(appProvider).inject(this)
    }

    override fun getContentView(): Int = R.layout.fragment_event_list

    override fun getViewModelClass(): Class<EventListViewModel> = EventListViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setClickEvent { viewId, pos, item ->
            //            val args = Bundle().apply { putString("photo", item.thumbnail) }
//            NavHostFragment.findNavController(this).navigate(R.id.actionEventDetail, args)
        }
        eventsGroupRecyclerView.adapter = adapter
        eventsGroupRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeCategories()
    }


    private fun subscribeCategories() {
        viewModel.observeCategories()
            .subscribe {
                when (it) {
                    is LoadingUiState -> {

                    }
                    is ErrorUiState -> {
                        shoeError(it)
                    }
                    is DataUiState -> {
                        it.data.let {
                            animateGroupRecycler()
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
                    when (it) {
                        is LoadingUiState -> {
                            adapter.showEventsLoading(id, it.isLoading)
                        }
                        is ErrorUiState -> {
                            shoeError(it)
                        }
                        is DataUiState -> {
                            eventsGroupRecyclerView.post { adapter.submitEvents(id, it.data) }
                        }
                    }
                }
                .addTo(destroyViewDisposable)
        }
    }

    private fun shoeError(it: ErrorUiState<*>) {
        Toast.makeText(context, it.throwable.message ?: "", Toast.LENGTH_LONG)
            .show()
    }


    private fun animateGroupRecycler() {
        if (adapter.getListSize() == 0) {
            TransitionManager.beginDelayedTransition(
                eventsGroupRecyclerView,
                TransitionSet().apply {
                    addTransition(Fade().setDuration(100))
                    addTransition(Slide(Gravity.BOTTOM).setDuration(200))
                }
            )
        }
    }
}
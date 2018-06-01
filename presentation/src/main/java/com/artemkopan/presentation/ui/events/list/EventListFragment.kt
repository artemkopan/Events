package com.artemkopan.presentation.ui.events.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.artemkopan.core.entity.CategoryEntity
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
    lateinit var adapter: EventsAdapter

    override fun inject(appProvider: ApplicationProvider) {
        EventsComponent.Initializer.init(appProvider).inject(this)
    }

    override fun getContentView(): Int = R.layout.fragment_event_list

    override fun getViewModelClass(): Class<EventListViewModel> = EventListViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.clickEvent = { viewId, pos, item ->
            val args = Bundle().apply { putString("photo", item.thumbnail) }

            NavHostFragment.findNavController(this).navigate(R.id.actionEventDetail, args)
        }
        eventsRecyclerView.adapter = adapter
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeCategories()
    }


    private fun subscribeCategories() {
        viewModel.observeCategories()
                .subscribe({
                    when {
                        it.isLoading -> {
                        }
                        it.isError -> {
                            Toast.makeText(context, it.throwable?.message ?: "", Toast.LENGTH_LONG)
                                    .show()
                        }
                        it.isSuccess -> {
                            //todo submit into adapter
                            it.data?.let { subscribeEvents(it) }
                        }
                    }
                })
                .addTo(destroyViewDisposable)
    }

    private fun subscribeEvents(categories: List<CategoryEntity>) {
        categories.forEach { (id) ->
            viewModel.observeEvents(id)
                    .subscribe {
                        when {
                            it.isLoading -> {
                            }
                            it.isError -> {
                            }
                            it.isSuccess -> {
                                adapter.submitList(it.data)
                            }
                        }
                    }
                    .addTo(destroyViewDisposable)
        }
    }

}
package com.artemkopan.presentation.ui.events.list

import android.os.Bundle
import android.support.transition.Slide
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.artemkopan.core.tools.Logger
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseFragment
import com.artemkopan.presentation.base.Injectable
import com.artemkopan.presentation.ui.events.EventsComponent
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.Consumer
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
//        viewModel.getCategories()
//                .subscribe(BiConsumer { t1, t2 ->
//                    Logger.d("test")
//                })
//
//        viewModel.getEvents()
//                .subscribe(Consumer {
//                    if (adapter.itemCount == 0) {
//                        TransitionManager.beginDelayedTransition(eventsRecyclerView, Slide(Gravity.BOTTOM))
//                    }
//                    adapter.submitList(it)
//                })
//                .addTo(destroyViewDisposable)
    }

}
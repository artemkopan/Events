package com.artemkopan.presentation.ui.events.list

import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.artemkopan.core.entity.CategoryEntity
import com.artemkopan.core.entity.EventEntity
import com.artemkopan.di.App
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.recycler.RecyclerStateManager
import com.artemkopan.presentation.base.recycler.adapters.BaseDataAdapter
import com.artemkopan.presentation.base.recycler.holders.BaseHolder
import com.artemkopan.presentation.extensions.dimen
import com.artemkopan.presentation.extensions.inflateView
import com.artemkopan.presentation.ui.events.list.EventsGroupAdapter.EventsGroupHolder
import kotlinx.android.synthetic.main.item_event_group.*
import javax.inject.Inject
import javax.inject.Provider


class EventsGroupAdapter @Inject constructor(private val app: App,
                                             private val eventsAdapterProvider: Provider<EventsAdapter>)
    : BaseDataAdapter<CategoryEntity, EventsGroupHolder>(DIFF_CALLBACK) {

    val eventsBundle = Bundle()

    private val viewPool = RecyclerView.RecycledViewPool()
    private val eventsData = ArrayMap<String, EventsAdapter>()
    private val eventsStates = SparseArray<RecyclerStateManager>()

    private val space by lazy { app.resources().dimen(R.dimen.event_item_space) }

    fun submitEvents(categoryId: String, data: PagedList<EventEntity>) {
        initEventsAdapterIfNeed(categoryId)
        eventsData[categoryId]!!.submitList(data)
    }

    fun showEventsLoading(categoryId: String, isLoading: Boolean) {
        initEventsAdapterIfNeed(categoryId)
        eventsData[categoryId]!!.showFooter(isLoading)
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): EventsGroupHolder {
        return EventsGroupHolder(parent.inflateView(R.layout.item_event_group))
    }

    private fun initEventsAdapterIfNeed(key: String) {
        if (!eventsData.containsKey(key)) {
            eventsData[key] = eventsAdapterProvider.get()
        }
    }

    inner class EventsGroupHolder(containerView: View) : BaseHolder<CategoryEntity>(containerView) {

        init {
            eventsRecyclerView.layoutManager = LinearLayoutManager(containerView.context, HORIZONTAL, false)
            eventsRecyclerView.isNestedScrollingEnabled = false
            eventsRecyclerView.addItemDecoration(EventsListItemDecoration(space))
            eventsRecyclerView.setRecycledViewPool(viewPool)
            eventsRecyclerView.setHasFixedSize(true)
        }

        override fun bind(item: CategoryEntity) {
            categoryTextView.text = item.name
            item.id.let { categoryId ->
                initEventsAdapterIfNeed(categoryId)
                eventsRecyclerView.adapter = eventsData[categoryId]
            }
            restoreState()
        }

        override fun recycled() {
            saveState()
            eventsRecyclerView.adapter = null
            super.recycled()
        }

        override fun bindClickListener(listener: View.OnClickListener) {
            detailListButton.setOnClickListener(listener)
        }

        override fun unbindClickListener() {
            detailListButton.setOnClickListener(null)
        }

        private fun saveState() {
            val position = adapterPosition
            val value: RecyclerStateManager = object : RecyclerStateManager() {
                override fun getStateTag(): String {
                    return "${KEY_STATE}_$position"
                }
            }
            value.saveState(eventsRecyclerView, eventsBundle)
            eventsStates.put(position, value)
        }

        private fun restoreState() {
            val position = adapterPosition
            eventsStates[position]?.let {
                it.restoreState(eventsBundle)
                it.applyState(eventsRecyclerView)
                eventsBundle.remove(it.getStateTag())
                eventsStates.remove(position)
            }
        }
    }


    companion object {
        private const val KEY_STATE = "EventsAdapter"

        @JvmStatic
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryEntity>() {
            override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity) = true
        }

    }
}


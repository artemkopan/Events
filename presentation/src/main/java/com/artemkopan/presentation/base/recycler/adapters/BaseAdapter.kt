package com.artemkopan.presentation.base.recycler.adapters

import android.view.View
import android.view.ViewGroup
import com.artemkopan.presentation.base.recycler.holders.BaseHolder
import kotlin.properties.Delegates


const val FOOTER = Int.MIN_VALUE + 1

interface BaseAdapter<T, VH : BaseHolder<T>> {

    fun setClickEvent(func: (viewId: Int, pos: Int, item: T?) -> Unit)

    fun showFooter(isShow: Boolean)

    fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): VH

    fun onCreateFooterViewHolder(parent: ViewGroup, viewType: Int): VH

    fun onBindItemViewHolder(holder: VH, position: Int)

    fun onBindFooterViewHolder(holder: VH, position: Int)

    fun getRealSize(): Int

    fun getItem(position: Int): T?

    fun isEmpty() : Boolean

    fun notifyItemInserted(position: Int)

    fun notifyItemRemoved(position: Int)

}

class BaseAdapterHelper<T, VH : BaseHolder<T>>(private val adapter: BaseAdapter<T, VH>) {

    var clickEvent: ((viewId: Int, pos: Int, item: T?) -> Unit)? = null

    var showFooter: Boolean by Delegates.observable(false, { _, oldValue, newValue ->
        if (oldValue == newValue) {
            return@observable
        }
        if (newValue) {
            adapter.notifyItemInserted(getItemCount())
        } else {
            adapter.notifyItemRemoved(getItemCount())
        }
    })

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return if (viewType == FOOTER) {
            adapter.onCreateFooterViewHolder(parent, viewType)
        } else {
            adapter.onCreateItemViewHolder(parent, viewType)
        }
    }

    fun onBindViewHolder(holder: VH, position: Int) {
        if (position < adapter.getRealSize()) {
            adapter.onBindItemViewHolder(holder, position)
        } else {
            adapter.onBindFooterViewHolder(holder, position)
        }
    }

    fun getItemCount(): Int {
        return if (showFooter) adapter.getRealSize() + 1 else adapter.getRealSize()
    }

    fun getItemViewType(position: Int): Int {
        return if (position >= adapter.getRealSize()) {
            FOOTER
        } else {
            return 0
        }
    }

    fun onViewAttachedToWindow(holder: VH) {
        holder.onViewAttachedToWindow()
        holder.bindClickListener(View.OnClickListener { view ->
            val pos = holder.adapterPosition
            clickEvent?.invoke(view.id, pos, adapter.getItem(pos))
        })
    }

    fun onViewDetachedFromWindow(holder: VH) {
        holder.onViewDetachedFromWindow()
        holder.unbindClickListener()
    }

    fun onViewRecycled(holder: VH) {
        holder.recycled()
        holder.clearViewCache()
    }
}
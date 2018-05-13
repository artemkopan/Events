package com.artemkopan.presentation.base.recycler

import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.View
import android.view.ViewGroup
import kotlin.properties.Delegates

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseAdapter<T, VH : BaseHolder<T>> : ListAdapter<T, VH> {

    var clickEvent: ((viewId: Int, pos: Int, item: T) -> Unit)? = null

    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)
    constructor(config: AsyncDifferConfig<T>) : super(config)

    //region Create and Bind methods

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return if (viewType == FOOTER) {
            onCreateFooterViewHolder(parent, viewType)
        } else {
            onCreateItemViewHolder(parent, viewType)
        }
    }

    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): VH

    protected open fun onCreateFooterViewHolder(parent: ViewGroup, viewType: Int): VH {
        throw NotImplementedError("method in not implemented")
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {
        if (position < getListSize()) {
            onBindItemViewHolder(holder, position)
        } else {
            onBindFooterViewHolder(holder, position)
        }
    }

    protected open fun onBindItemViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    protected open fun onBindFooterViewHolder(holder: VH, position: Int) {
        // override method for implementation
    }

    //endregion

    //region Holder LifeCycle methods

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.bindClickListener(View.OnClickListener { view ->
            val pos = holder.adapterPosition
            clickEvent?.invoke(view.id, pos, getItem(pos))
        })
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.unbindClickListener()
    }

    override fun onViewRecycled(holder: VH) {
        holder.recycled()
    }

    //endregion

    public override fun getItem(position: Int): T {
        return super.getItem(position)
    }

    override fun getItemCount(): Int {
        return if (showFooter) getListSize() + 1 else getListSize()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= getListSize()) {
            FOOTER
        } else {
            return super.getItemViewType(position)
        }
    }

    fun getListSize(): Int = super.getItemCount()

    var showFooter: Boolean by Delegates.observable(false, { _, oldValue, newValue ->
        if (oldValue == newValue) {
            return@observable
        }
        if (newValue) {
            notifyItemInserted(itemCount)
        } else {
            notifyItemRemoved(itemCount)
        }
    })

    companion object {
        const val FOOTER = Int.MIN_VALUE + 1
    }

}
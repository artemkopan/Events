package com.artemkopan.presentation.base.recycler.adapters

import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.artemkopan.presentation.base.recycler.holders.BaseHolder

@Suppress("unused", "MemberVisibilityCanBePrivate", "LeakingThis", "RedundantOverride")
abstract class BaseDataAdapter<T, VH : BaseHolder<T>> : ListAdapter<T, VH>, BaseAdapter<T, VH> {

    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)
    constructor(config: AsyncDifferConfig<T>) : super(config)

    private val helper = BaseAdapterHelper(this)

    override fun setClickEvent(func: (viewId: Int, pos: Int, item: T?) -> Unit) {
        helper.clickEvent = func
    }

    override fun showFooter(isShow: Boolean) {
        helper.showFooter = isShow
    }

    //region Create and Bind methods

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return helper.onCreateViewHolder(parent, viewType)
    }

    override fun onCreateFooterViewHolder(parent: ViewGroup, viewType: Int): VH {
        throw NotImplementedError("method in not implemented")
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {
        helper.onBindViewHolder(holder, position)
    }

    override fun onBindItemViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onBindFooterViewHolder(holder: VH, position: Int) {
        // override method for implementation
    }

    //endregion

    //region Holder LifeCycle methods

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        helper.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        helper.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: VH) {
        helper.onViewRecycled(holder)
    }

    //endregion

    override fun getItem(position: Int): T? {
        return super.getItem(position)
    }

    override fun getItemCount(): Int {
        return helper.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return helper.getItemViewType(position)
    }

    override fun getListSize(): Int = super.getItemCount()


}
package com.artemkopan.presentation.base.recycler.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.CacheImplementation
import kotlinx.android.extensions.ContainerOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.*

// Do not use CACHE in base classes because it creates unused HashMap.
// An each inherit holder creates new cache map
@ContainerOptions(CacheImplementation.NO_CACHE)
abstract class BaseHolder<T>(override val containerView: View)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {

    open fun onViewAttachedToWindow() {}

    abstract fun bind(item: T)

    open fun onViewDetachedFromWindow() {}

    abstract fun bindClickListener(listener: View.OnClickListener)

    abstract fun unbindClickListener()

    open fun recycled() {}

    fun clearViewCache() {
        clearFindViewByIdCache()
    }

}

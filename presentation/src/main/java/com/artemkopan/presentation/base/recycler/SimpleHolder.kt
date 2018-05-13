package com.artemkopan.presentation.base.recycler

import android.view.View

class SimpleHolder<T>(containerView: View) : BaseHolder<T>(containerView) {

    override fun bind(item: T) {}

    override fun bindClickListener(listener: View.OnClickListener) {}

    override fun unbindClickListener() {}

}
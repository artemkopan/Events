package com.artemkopan.presentation.ui.events.list

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class EventsListItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        if (position == parent.adapter!!.itemCount - 1) {
            outRect.right = space
        } else {
            outRect.right = 0
        }
    }

}

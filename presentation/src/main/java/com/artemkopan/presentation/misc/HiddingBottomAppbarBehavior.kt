package com.artemkopan.presentation.misc

import android.content.Context
import android.support.design.bottomappbar.BottomAppBar
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.HideBottomViewOnScrollBehavior
import android.util.AttributeSet
import android.view.View

class HiddingBottomAppbarBehavior : CoordinatorLayout.Behavior<View> {

    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    private val scrollBehavior = HideBottomViewOnScrollBehavior<View>()
    private val bottomBarBehavior = BottomAppBar.Behavior()


    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        if (child is BottomAppBar) {
           return bottomBarBehavior.onLayoutChild(parent, child, layoutDirection)
        }
        return scrollBehavior.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return scrollBehavior.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        return scrollBehavior.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
    }
}

package com.artemkopan.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View

class StatusBarView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : View(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val statusBarHeight = getStatusBarHeight()
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), statusBarHeight)
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

}
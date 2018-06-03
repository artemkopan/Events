package com.artemkopan.presentation.base.recycler

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import com.artemkopan.core.tools.Logger

@Suppress("MemberVisibilityCanBePrivate")
open class RecyclerStateManager {

    private var recyclerState: Parcelable? = null

    fun hasState() = recyclerState != null

    fun applyState(recyclerView: RecyclerView) {
        recyclerState?.let { state ->
            recyclerView.layoutManager?.apply {
                onRestoreInstanceState(state)
                recyclerState = null
            } ?: Logger.w(LOG_TAG, "Recycler's layout manager null")
        } ?: Logger.d(LOG_TAG, "Make sure that state was restored firstly RecyclerStateManager#restoreState")
    }

    fun restoreState(bundle: Bundle?) {
        if (bundle == null) {
            return
        }
        recyclerState = bundle.getParcelable(getStateTag())
    }

    fun saveState(recyclerView: RecyclerView, bundle: Bundle?) {
        saveState(recyclerView.layoutManager, bundle)
    }

    fun saveState(layoutManager: RecyclerView.LayoutManager?, bundle: Bundle?) {
        if (bundle == null || layoutManager == null) {
            Logger.w(LOG_TAG, "Wrong parameters for saving state, bundle = $bundle, " +
                    "layout manager = $layoutManager")
            return
        }
        bundle.putParcelable(getStateTag(), layoutManager.onSaveInstanceState())
    }

    open fun getStateTag() = KEY_STATE_GROUP

    companion object {
        private const val LOG_TAG = "RecyclerStateManager"
        private const val KEY_STATE_GROUP = "STATE_GROUP"
    }
}
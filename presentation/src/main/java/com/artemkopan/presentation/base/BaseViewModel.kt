package com.artemkopan.presentation.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    protected val clearDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {

    }

   override fun onCleared() {
        clearDisposable.clear()
    }

}
package com.artemkopan.presentation.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
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
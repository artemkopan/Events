package com.artemkopan.core.tools

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

abstract class UiStateSubject<T> : Disposable {

    private val subject: Subject<UiState<T>> = BehaviorSubject.create()
    private var disposable: Disposable? = null

    fun observer(): Observable<UiState<T>> = subject

    fun loadData() {
        disposable = createData(subject)
    }

    abstract fun createData(subject: Subject<UiState<T>>): Disposable

    override fun isDisposed(): Boolean = disposable.isDisposedSafe()

    override fun dispose() {
        disposable?.dispose()
    }
}
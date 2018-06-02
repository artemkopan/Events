package com.artemkopan.core.tools

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

abstract class UiStateSubject<Payload, Result> : Disposable {

    private val subject: Subject<UiState<Result>> = BehaviorSubject.create()
    private var disposable: Disposable? = null

    fun observer(): Observable<UiState<Result>> = subject

    fun loadData(payload: Payload?) {
        disposable = createData(subject, payload)
    }

    abstract fun createData(subject: Subject<UiState<Result>>, payload: Payload? = null): Disposable

    override fun isDisposed(): Boolean = disposable.isDisposedSafe()

    override fun dispose() {
        disposable?.dispose()
    }
}
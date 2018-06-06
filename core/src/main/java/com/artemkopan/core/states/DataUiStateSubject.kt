package com.artemkopan.core.states

import com.artemkopan.core.tools.isDisposedSafe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

abstract class DataUiStateSubject<Payload, Result> : UiStateSubject<Payload, Result> {

    private val subject: Subject<UiState<Result>> = BehaviorSubject.create()
    private var disposable: Disposable? = null

    override fun sendState(state: UiState<Result>) {
        subject.onNext(state)
    }

    override fun observer(): Observable<UiState<Result>> = subject

    override fun loadData(payload: Payload?) {
        disposable = createData(payload)
    }

    abstract fun createData(payload: Payload?): Disposable

    override fun isDisposed(): Boolean = disposable.isDisposedSafe()

    override fun dispose() {
        disposable?.dispose()
    }
}

fun <T> Single<T>.subscribe(stateSubject: UiStateSubject<*, T>): Disposable {
    return this
            .doOnSubscribe { stateSubject.sendState(UiState(true)) }
            .subscribe({
                stateSubject.sendState(UiState(it))
            }, {
                stateSubject.sendState(UiState(it))
            })
}

package com.artemkopan.core.tools

import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.PagedList
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject


interface UiStateSubject<Payload, Result> : Disposable {

    fun observer(): Observable<UiState<Result>>

    fun loadData(payload: Payload? = null)

}

//region Rx Subscribe extensions

fun <T, S> Single<T>.subscribe(subject: Subject<UiState<S>>, onSuccess: (T) -> Unit): Disposable {
    return this
        .doOnSubscribe { subject.onNext(LoadingUiState(true)) }
        .subscribe({
                       subject.onNext(LoadingUiState(false))
                       onSuccess(it)
                   },
                   {
                       subject.onNext(LoadingUiState(false))
                       subject.onNext(ErrorUiState(it))
                   })
}

fun <T> Single<T>.subscribe(subject: Subject<UiState<T>>): Disposable {
    return this
        .doOnSubscribe { subject.onNext(LoadingUiState(true)) }
        .subscribe({
                       subject.onNext(LoadingUiState(false))
                       subject.onNext(DataUiState(it))
                   },
                   {
                       subject.onNext(LoadingUiState(false))
                       subject.onNext(ErrorUiState(it))
                   })
}

//endregion

//region Data State Subjects

abstract class DataUiStateSubject<Payload, Result> : UiStateSubject<Payload, Result> {

    private var disposable: Disposable? = null
    protected val subject: Subject<UiState<Result>> = BehaviorSubject.create()

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

//endregion

//region Pagination State Subjects

abstract class PagedListKeyedUiStateSubject<Key, Value, Payload>
    : ItemKeyedDataSource<Key, Value>(), UiStateSubject<Payload, PagedList<Value>> {

    protected val subject: Subject<UiState<PagedList<Value>>> = BehaviorSubject.create()
    protected var compositeDisposable: CompositeDisposable? = null

    override fun observer(): Observable<UiState<PagedList<Value>>> = subject

    override fun loadData(payload: Payload?) {
        compositeDisposable?.dispose()
        compositeDisposable = CompositeDisposable()
        val pagedList = createPagedList(payload)
        subject.onNext(DataUiState(pagedList))
    }

    abstract fun createPagedList(payload: Payload?): PagedList<Value>

    override fun isDisposed(): Boolean = compositeDisposable.isDisposedSafe()

    override fun dispose() {
        invalidate()
        compositeDisposable?.dispose()
    }
}

//endregion
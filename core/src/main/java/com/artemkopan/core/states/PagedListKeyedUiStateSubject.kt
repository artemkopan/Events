package com.artemkopan.core.states

import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.PagedList
import com.artemkopan.core.tools.isDisposedSafe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

abstract class PagedListKeyedUiStateSubject<Key, Result, Payload>
    : ItemKeyedDataSource<Key, Result>(), UiPagedListStateSubject<Payload, Result> {

    protected var compositeDisposable: CompositeDisposable? = null
    private val subject: BehaviorSubject<UiState<PagedList<Result>>> = BehaviorSubject.create()
    private var pagedList: PagedList<Result>? = null

    override fun sendState(state: UiState<PagedList<Result>>) {
        subject.onNext(state)
    }

    override fun observer(): Observable<UiState<PagedList<Result>>> = subject

    override fun loadData(payload: Payload?) {
        compositeDisposable?.dispose()
        compositeDisposable = CompositeDisposable()
        pagedList = create(payload)
    }

    override fun isDisposed(): Boolean = compositeDisposable.isDisposedSafe()

    override fun dispose() {
        invalidate()
        compositeDisposable?.dispose()
    }

    override fun getPagedList(): PagedList<Result>? = pagedList

}

fun <T, R> Single<T>.subscribe(listStateSubject: UiPagedListStateSubject<*, R>,
                               onSuccess: (T) -> Unit): Disposable {
    return this
            .doOnSubscribe { listStateSubject.sendState(UiState(true)) }
            .subscribe({
                listStateSubject.sendState(UiState(listStateSubject.getPagedList()))
                onSuccess(it)
            }, {
                listStateSubject.sendState(UiState(it))
            })
}

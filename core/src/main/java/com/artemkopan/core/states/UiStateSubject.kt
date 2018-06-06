package com.artemkopan.core.states

import android.arch.paging.PagedList
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface UiStateSubject<Payload, Result> : Disposable {

    fun observer(): Observable<UiState<Result>>

    fun loadData(payload: Payload? = null)

    fun sendState(state: UiState<Result>)
}


interface UiPagedListStateSubject<Payload, Result> : UiStateSubject<Payload, PagedList<Result>> {

    fun create(payload: Payload?): PagedList<Result>

    fun getPagedList(): PagedList<Result>?

}
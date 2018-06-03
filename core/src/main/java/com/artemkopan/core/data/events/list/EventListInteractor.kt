package com.artemkopan.core.data.events.list

import android.arch.paging.PagedList
import com.artemkopan.core.entity.EventEntity
import com.artemkopan.core.tools.UiState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface EventListInteractor : Disposable {

    fun loadEvents(categoryId: String, lastItem: EventEntity? = null)

    fun observer(categoryId: String): Observable<UiState<PagedList<EventEntity>>>

}

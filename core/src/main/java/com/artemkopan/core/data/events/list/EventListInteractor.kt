package com.artemkopan.core.data.events.list

import com.artemkopan.core.entity.EventEntity
import com.artemkopan.core.tools.UiState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface EventListInteractor : Disposable {

    fun setCategoryId(categoryId: String)

    fun loadEvents()

    fun observer(): Observable<UiState<List<EventEntity>>>

}

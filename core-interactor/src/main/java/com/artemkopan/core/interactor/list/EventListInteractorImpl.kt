package com.artemkopan.core.interactor.list

import com.artemkopan.core.Const
import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.core.data.events.list.EventListInteractor
import com.artemkopan.core.entity.EventEntity
import com.artemkopan.core.tools.Logger
import com.artemkopan.core.tools.UiState
import com.artemkopan.core.tools.isDisposedSafe
import com.artemkopan.core.tools.subscribe
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class EventListInteractorImpl @Inject constructor(private val eventsNetworkClient: EventsNetworkClient)
    : EventListInteractor {


    private val subject: Subject<UiState<List<EventEntity>>> = BehaviorSubject.create()
    private var loadDisposable: Disposable? = null
    private lateinit var categoryId: String

    override fun setCategoryId(categoryId: String) {
        this.categoryId = categoryId
    }

    override fun loadEvents() {
        eventsNetworkClient.getEvents(categoryId, 1)
                .subscribeOn(Schedulers.io())
                .doOnError {
                    Logger.e(Const.Tag.EVENT_LIST, "Error load movie list: category " +
                            "$categoryId, page $'page", it)
                }
                .subscribe(subject)
    }


    override fun observer(): Observable<UiState<List<EventEntity>>> = subject

    override fun dispose() {
        loadDisposable?.dispose()
    }

    override fun isDisposed(): Boolean = loadDisposable.isDisposedSafe()
}

package com.artemkopan.core.interactor.list

import com.artemkopan.core.Const
import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.core.data.events.list.EventListInteractor
import com.artemkopan.core.entity.EventEntity
import com.artemkopan.core.tools.Logger
import com.artemkopan.core.tools.UiState
import com.artemkopan.core.tools.UiStateSubject
import com.artemkopan.core.tools.subscribe
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.Subject
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class EventListInteractorImpl @Inject constructor(private val eventsNetworkClient: EventsNetworkClient) : EventListInteractor {

    companion object {
        private const val LIMIT = 10
        private const val START_PAGE = 1
    }

    private val categoriesData: MutableMap<String, UiStateSubject<Int, List<EventEntity>>> = ConcurrentHashMap()

    override fun loadEvents(categoryId: String) {
        initDataSubject(categoryId)
        categoriesData[categoryId]!!.loadData(START_PAGE)
    }

    override fun observer(categoryId: String): Observable<UiState<List<EventEntity>>> {
        initDataSubject(categoryId)
        return categoriesData[categoryId]!!.observer()
    }

    override fun dispose() {
        categoriesData.values.forEach { it.dispose() }
    }

    override fun isDisposed(): Boolean = false

    private fun initDataSubject(categoryId: String) {
        if (!categoriesData.containsKey(categoryId)) {
            categoriesData[categoryId] = EventsStateSubject(categoryId)
        }
    }

    private inner class EventsStateSubject(private val categoryId: String) : UiStateSubject<Int, List<EventEntity>>() {

        override fun createData(subject: Subject<UiState<List<EventEntity>>>, payload: Int?): Disposable {
            return eventsNetworkClient.getEvents(categoryId, payload!!, LIMIT)
                .subscribeOn(Schedulers.io())
                .doOnError {
                    Logger.e(
                        Const.Tag.EVENT_LIST, "Error load movie list: category " +
                                "$categoryId, page $'page", it
                    )
                }
                .subscribe(subject)
        }
    }

}

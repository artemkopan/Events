package com.artemkopan.core.interactor.list

import android.arch.paging.PagedList
import com.artemkopan.core.Const
import com.artemkopan.core.data.SystemRepository
import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.core.data.events.list.EventListInteractor
import com.artemkopan.core.entity.EventEntity
import com.artemkopan.core.tools.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class EventListInteractorImpl @Inject constructor(private val eventsNetworkClient: EventsNetworkClient,
                                                  private val systemRepository: SystemRepository)
    : EventListInteractor {

    companion object {
        private const val LIMIT = 10
    }

    private val categoriesData: MutableMap<String, UiStateSubject<Any, PagedList<EventEntity>>> = ConcurrentHashMap()

    override fun loadEvents(categoryId: String, lastItem: EventEntity?) {
        initDataSubject(categoryId)
        categoriesData[categoryId]!!.loadData()
    }

    override fun observer(categoryId: String): Observable<UiState<PagedList<EventEntity>>> {
        initDataSubject(categoryId)
        return categoriesData[categoryId]!!.observer()
    }

    override fun dispose() = categoriesData.values.forEach { it.dispose() }

    override fun isDisposed(): Boolean = false

    private fun initDataSubject(categoryId: String) {
        if (!categoriesData.containsKey(categoryId)) {
            categoriesData[categoryId] = EventsStateSubject(categoryId)
        }
    }

    private inner class EventsStateSubject(private val categoryId: String)
        : PagedListKeyedUiStateSubject<String, EventEntity, Any>() {

        override fun createPagedList(payload: Any?): PagedList<EventEntity> {
            val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(LIMIT)
                .build()

            return PagedList.Builder(this, config)
                .setFetchExecutor(systemRepository.getFetchExecutor())
                .setNotifyExecutor(systemRepository.getMainExecutor())
                .build()
        }

        override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<EventEntity>) {
            getEvents(null, params.requestedLoadSize, callback)
        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<EventEntity>) {
            getEvents(params.key, params.requestedLoadSize, callback)
        }

        override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<EventEntity>) {

        }

        override fun getKey(item: EventEntity) = item.id

        fun getEvents(startAfterId: String?, limit: Int, callback: LoadCallback<EventEntity>) {
            compositeDisposable?.clear()

            eventsNetworkClient.getEvents(categoryId, startAfterId, limit.toLong())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { compositeDisposable?.add(it) }
                .doOnError {
                    Logger.e(
                        Const.Tag.EVENT_LIST, "Error load movie list: category " +
                                "$categoryId, page $'page", it
                    )
                }
                .subscribe(subject, { callback.onResult(it) })
        }
    }
}

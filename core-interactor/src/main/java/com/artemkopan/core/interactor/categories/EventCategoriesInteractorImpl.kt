package com.artemkopan.core.interactor.categories

import com.artemkopan.core.data.SystemRepository
import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.core.data.events.categories.EventCategoriesInteractor
import com.artemkopan.core.entity.CategoryEntity
import com.artemkopan.core.states.DataUiStateSubject
import com.artemkopan.core.states.UiState
import com.artemkopan.core.states.subscribe
import com.artemkopan.core.tools.Logger
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class EventCategoriesInteractorImpl @Inject constructor(
        private val client: EventsNetworkClient,
        private val systemRepo: SystemRepository
) : EventCategoriesInteractor {


    override fun observer(): Observable<UiState<List<CategoryEntity>>> = categoriesSubject.observer()

    override fun loadCategories() = categoriesSubject.loadData()

    override fun dispose() {
        categoriesSubject.dispose()
    }

    override fun isDisposed(): Boolean = categoriesSubject.isDisposed

    private val categoriesSubject = object : DataUiStateSubject<Any, List<CategoryEntity>>() {
        override fun createData(payload: Any?): Disposable =
                client.getCategories(systemRepo.getCurrentLocal() ?: "")
                        .subscribeOn(Schedulers.io())
                        .doOnError { Logger.e("Failure load categories") }
//                        .map { listOf(it[0]) /* todo remove */ }
                        .subscribe(this)
    }

}
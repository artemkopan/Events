package com.artemkopan.core.interactor.categories

import com.artemkopan.core.data.SystemRepository
import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.core.data.events.categories.EventCategoriesInteractor
import com.artemkopan.core.entity.CategoryEntity
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


class EventCategoriesInteractorImpl @Inject constructor(private val client: EventsNetworkClient,
                                                        private val systemRepo: SystemRepository) : EventCategoriesInteractor {


    private val subject: Subject<UiState<List<CategoryEntity>>> = BehaviorSubject.create()
    private var loadDisposable: Disposable? = null


    override fun observer(): Observable<UiState<List<CategoryEntity>>> = subject

    override fun loadCategories() {
        loadDisposable = client.getCategories(systemRepo.getCurrentLocal() ?: "")
                .subscribeOn(Schedulers.io())
                .doOnError { Logger.e("Failure load categories") }
                .subscribe(subject)
    }

    override fun dispose() {
        loadDisposable?.dispose()
    }

    override fun isDisposed(): Boolean = loadDisposable.isDisposedSafe()

}
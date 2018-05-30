package com.artemkopan.core.interactor.categories

import com.artemkopan.core.data.SystemRepository
import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.core.data.events.categories.EventCategoriesInteractor
import com.artemkopan.core.entity.CategoryEntity
import io.reactivex.Single
import javax.inject.Inject


class EventCategoriesInteractorImpl @Inject constructor(private val client: EventsNetworkClient,
                                                        private val systemRepo: SystemRepository) : EventCategoriesInteractor {

    override fun getCategories(): Single<List<CategoryEntity>> {
        return client.getCategories(systemRepo.getCurrentLocal() ?: "")
    }

}
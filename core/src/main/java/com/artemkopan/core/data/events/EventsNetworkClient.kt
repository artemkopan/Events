package com.artemkopan.core.data.events

import com.artemkopan.core.entity.CategoryEntity
import com.artemkopan.core.entity.EventEntity
import io.reactivex.Single

interface EventsNetworkClient {

    fun getCategories(locale: String): Single<List<CategoryEntity>>

    fun getEvents(page: Int): Single<List<EventEntity>>

    fun getEvent(id: String): Single<EventEntity>

}
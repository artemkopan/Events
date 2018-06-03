package com.artemkopan.core.data.events

import com.artemkopan.core.entity.CategoryEntity
import com.artemkopan.core.entity.EventEntity
import io.reactivex.Single

interface EventsNetworkClient {

    fun getCategories(locale: String): Single<List<CategoryEntity>>

    fun getEvents(categoryId: String, startAfterId: String?, limit: Long): Single<List<EventEntity>>

    fun getEvent(id: String): Single<EventEntity>

}
package com.artemkopan.core.data.events

import com.artemkopan.core.entity.EventEntity
import io.reactivex.Single

interface EventsNetworkClient {

    fun getEvents(page: Int): Single<List<EventEntity>>

}
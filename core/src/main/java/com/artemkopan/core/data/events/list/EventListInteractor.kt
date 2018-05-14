package com.artemkopan.core.data.events.list

import com.artemkopan.core.entity.EventEntity
import io.reactivex.Single

interface EventListInteractor {

    fun getEvents(): Single<List<EventEntity>>

}

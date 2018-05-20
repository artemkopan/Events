package com.artemkopan.core.data.events.detail

import com.artemkopan.core.entity.EventEntity
import io.reactivex.Observable

interface EventDetailInteractor {

    fun getEvent(id: String): Observable<EventEntity>

}
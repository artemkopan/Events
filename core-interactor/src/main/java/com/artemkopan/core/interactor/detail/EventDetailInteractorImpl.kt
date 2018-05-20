package com.artemkopan.core.interactor.detail

import com.artemkopan.core.data.events.detail.EventDetailInteractor
import com.artemkopan.core.entity.EventEntity
import io.reactivex.Observable
import javax.inject.Inject

class EventDetailInteractorImpl @Inject constructor() : EventDetailInteractor {
    override fun getEvent(id: String): Observable<EventEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
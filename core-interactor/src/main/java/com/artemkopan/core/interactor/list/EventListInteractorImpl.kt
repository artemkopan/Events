package com.artemkopan.core.interactor.list

import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.core.data.events.list.EventListInteractor
import com.artemkopan.core.entity.EventEntity
import io.reactivex.Single
import javax.inject.Inject

class EventListInteractorImpl @Inject constructor(private val eventsNetworkClient: EventsNetworkClient)
    : EventListInteractor {


    override fun getEvents(): Single<List<EventEntity>> {
        return eventsNetworkClient.getEvents(1)
    }


}

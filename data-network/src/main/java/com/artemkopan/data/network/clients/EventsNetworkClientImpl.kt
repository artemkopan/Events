package com.artemkopan.data.network.clients

import com.artemkopan.core.data.events.EventsNetworkClient
import javax.inject.Inject

class EventsNetworkClientImpl @Inject constructor(private val service: EventsService)
    : EventsNetworkClient {



}
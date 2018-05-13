package artemkopan.com.data.network.clients

import artemkopan.com.core.data.events.EventsNetworkClient
import javax.inject.Inject

class EventsNetworkClientImpl @Inject constructor(private val service: EventsService)
    : EventsNetworkClient {



}
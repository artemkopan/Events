//package com.artemkopan.data.network.clients
//
//import com.artemkopan.core.data.events.EventsNetworkClient
//import com.artemkopan.core.entity.CategoryEntity
//import com.artemkopan.core.entity.EventEntity
//import io.reactivex.Single
//import javax.inject.Inject
//
//class EventsNetworkClientImpl @Inject constructor(private val service: EventsService)
//    : EventsNetworkClient {
//    override fun getCategories(locale: String): Single<List<CategoryEntity>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getEvent(id: String): Single<EventEntity> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getEvents(page: Int): Single<List<EventEntity>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//}
package com.artemkopan.data.network.clients

import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.core.entity.CategoryEntity
import com.artemkopan.core.entity.EventEntity
import com.artemkopan.data.network.fromTask
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import javax.inject.Inject

class EventsFirestoreClient @Inject constructor() : EventsNetworkClient {

    companion object {
        private const val PATH_CATEGORIES = "categories"
        private const val PATH_EVENTS = "events"
    }

    private val store by lazy { FirebaseFirestore.getInstance() }
    private val storeExecutor by lazy { Executors.newSingleThreadExecutor() }
    private val storeScheduler: Scheduler
        get() = Schedulers.from(storeExecutor)

    override fun getCategories(locale: String): Single<List<CategoryEntity>> {
        return fromTask(store.collection(PATH_CATEGORIES).get(), storeScheduler)
                .flatMapPublisher { Flowable.fromIterable(it) }
                .observeOn(Schedulers.io())
                .map {
                    val name = it[locale]
                            ?: it.data.keys.firstOrNull()?.let { firstKey -> it[firstKey] }
                    CategoryEntity(it.id, name?.toString() ?: "")
                }
                .toList()
    }


    override fun getEvents(categoryId: String, page: Int, limit: Int): Single<List<EventEntity>> {
        return fromTask(store.collection(PATH_EVENTS).limit(limit.toLong()).get(), storeScheduler)
                .map {
                    val events = arrayListOf<EventEntity>()
                    it.documents.forEach { doc ->
                        events.add(EventEntity(
                                doc["id"] as Long,
                                doc["address"] as String,
                                null,
                                doc["name"] as String,
                                doc["hot"] as Boolean,
                                null
                        ))
                    }
                    return@map events
                }
    }

    override fun getEvent(id: String): Single<EventEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
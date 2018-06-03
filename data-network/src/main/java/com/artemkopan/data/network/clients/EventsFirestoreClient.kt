package com.artemkopan.data.network.clients

import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.core.entity.CategoryEntity
import com.artemkopan.core.entity.EventEntity
import com.artemkopan.core.entity.ProviderEntity
import com.artemkopan.data.network.fromTask
import com.google.firebase.firestore.*
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EventsFirestoreClient @Inject constructor() : EventsNetworkClient {

    companion object {
        private const val PATH_CATEGORIES = "categories"
        private const val PATH_EVENTS = "events"
        private const val PATH_EVENT_ITEMS = "items"
    }

    private val store by lazy { FirebaseFirestore.getInstance() }
    //    private val storeExecutor by lazy { Executors.newFixedThreadPool(4) }
    private val storeScheduler: Scheduler
        get() = Schedulers.io()


    //region Public methods

    override fun getCategories(locale: String): Single<List<CategoryEntity>> {
        return fromTask(store.collection(PATH_CATEGORIES).get(), storeScheduler)
            .flatMapPublisher { Flowable.fromIterable(it) }
            .observeOn(Schedulers.io())
            .map {
                val name = it[locale] ?: it.data.keys.firstOrNull()?.let { firstKey -> it[firstKey] }
                CategoryEntity(it.id, name?.toString() ?: "")
            }
            .toList()
    }

    override fun getEvent(id: String): Single<EventEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEvents(categoryId: String, startAfterId: String?, limit: Long): Single<List<EventEntity>> {
        return if (startAfterId != null) {
            fromTask(getEventsRef(categoryId).document(startAfterId).get(), storeScheduler)
                .map { initEventsRefParams(getEventsRef(categoryId), limit, it) }
        } else {
            Single.just(initEventsRefParams(getEventsRef(categoryId), limit))
        }
            .flatMap { query -> fromTask(query.get(), storeScheduler) }
            .map { return@map parseEventsList(it) }
    }

    //endregion

    //region Inner methods

    private fun getEventsRef(categoryId: String): CollectionReference {
        return store.collection(PATH_EVENTS)
            .document(categoryId)
            .collection(PATH_EVENT_ITEMS)
    }

    private fun initEventsRefParams(ref: CollectionReference,
                                    limit: Long,
                                    startAfter: DocumentSnapshot? = null): Query {
        return ref.orderBy("createdAt", Query.Direction.ASCENDING)
            .let { query -> startAfter?.let { query.startAfter(it) } ?: query }
            .limit(limit)
    }

    //TODO refactor this
    private fun parseEventsList(it: QuerySnapshot): ArrayList<EventEntity> {
        val events = arrayListOf<EventEntity>()
        it.documents.forEach { doc ->
            val thumbnail = with((doc["photos"] as List<Map<String, Any>>)) {
                if (isNotEmpty()) this[0]["original"] as String? else ""
            }
            val provider = (doc["provider"] as Map<String, Any>)["name"] as String?

            events.add(
                EventEntity(
                    doc["id"] as String,
                    ProviderEntity(name = provider),
                    doc["name"] as String,
                    doc["hot"] as Boolean,
                    thumbnail
                )
            )
        }
        return events
    }

    //endregion
}
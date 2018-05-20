package com.artemkopan.data.network.clients

import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.core.entity.EventEntity
import com.artemkopan.core.entity.PhotoEntity
import com.artemkopan.data.network.mappers.EventMapper
import com.artemkopan.data.network.mappers.LocationMapper
import com.artemkopan.data.network.mappers.ProviderMapper
import com.artemkopan.data.network.response.EventResponse
import com.artemkopan.data.network.response.LocationResponse
import com.artemkopan.data.network.response.ProviderResponse
import com.artemkopan.di.App
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import io.reactivex.Single
import javax.inject.Inject


class EventsNetworkClientMock @Inject constructor(private val app: App,
                                                  private val gson: Gson) : EventsNetworkClient {

    override fun getEvents(page: Int): Single<List<EventEntity>> {
        return getFileName(page)?.let { parseEvents(it) } ?: Single.just(emptyList())
    }

    override fun getEvent(id: String): Single<EventEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun parseEvents(fileName: String): Single<List<EventEntity>> {
        return Single.fromCallable { readFromFile(fileName) }
                .map {
                    parseJson(JsonParser().parse(it))
                }

    }

    private fun parseJson(jsonElement: JsonElement): List<EventEntity> {
        val items = jsonElement.asJsonObject["content"].asJsonObject["items"].asJsonArray
                .map { it.asJsonObject }

        return items.map {
            val eventResponse = gson.fromJson(it, EventResponse::class.java)
            val locationResponse = gson.fromJson(it["location"], LocationResponse::class.java)
            val providerResponse = gson.fromJson(it["provider"], ProviderResponse::class.java)

            val photos = mutableListOf<PhotoEntity>()

            it["albums"].asJsonArray
                    .map {
                        it.asJsonObject["photos"].asJsonArray
                                .map { it.asJsonObject }
                                .onEach {
                                    photos.add(PhotoEntity(
                                            it["original"].asString,
                                            it["thumbnails"].asJsonObject["small"].asString)
                                    )
                                }
                    }

            EventMapper(
                    ProviderMapper().map(providerResponse),
                    photos
            ).map(eventResponse)
        }
    }

    private fun getFileName(page: Int) = when (page) {
        1 -> "services_page_$page.json"
        2 -> "services_page_$page.json"
        3 -> "services_page_$page.json"
        4 -> "services_page_$page.json"
        5 -> "services_page_$page.json"
        else -> null
    }

    private fun readFromFile(fileName: String) = app.applicationContext().assets.open(fileName)
            .bufferedReader()
            .use {
                it.readText()
            }
}
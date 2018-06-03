package com.artemkopan.data.network.mappers

import com.artemkopan.core.entity.EventEntity
import com.artemkopan.core.entity.LocationEntity
import com.artemkopan.core.entity.PhotoEntity
import com.artemkopan.core.entity.ProviderEntity
import com.artemkopan.core.tools.Mapper
import com.artemkopan.data.network.response.EventResponse
import com.artemkopan.data.network.response.LocationResponse
import com.artemkopan.data.network.response.ProviderResponse


//class EventDetailMapper()
//
//class EventMapper(private val providerEntity: ProviderEntity,
//                  private val photos: List<PhotoEntity>) : Mapper<EventEntity, EventResponse>() {
//    override fun map(from: EventResponse) = with(from) {
//        EventEntity(
//                id?.toLong(),
//                address,
//                providerEntity,
//                name,
//                hot,
//                if (photos.isNotEmpty()) photos[0].small ?: "" else ""
//        )
//    }
//}
//
//class LocationMapper : Mapper<LocationEntity, LocationResponse>() {
//    override fun map(from: LocationResponse) = with(from) {
//        LocationEntity(latitude ?: 0.0, longitude ?: 0.0)
//    }
//}
//
//class ProviderMapper : Mapper<ProviderEntity, ProviderResponse>() {
//    override fun map(from: ProviderResponse) = with(from) {
//        ProviderEntity(name, id, phone?.normalizedPhoneNumber)
//    }
//}

//class ScheduleMapper : Mapper<ScheduleEntity, ScheduleResponse>(){
//    override fun map(from: ScheduleResponse) = with(from){
//
//        ScheduleEntity(timeFrom, timeTo, enabled, )
//    }
//}
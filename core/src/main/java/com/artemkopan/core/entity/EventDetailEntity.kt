package com.artemkopan.core.entity

data class EventDetailEntity(
        val id: Int?,
        val address: String?,
        val provider: ProviderEntity?,
        val name: String?,
        val description: String,
        val location: LocationEntity?,
        val hot: Boolean?,
        val thumbnail: String,
        val photos: List<PhotoEntity>?,
        val schedulers: List<ScheduleEntity>
)

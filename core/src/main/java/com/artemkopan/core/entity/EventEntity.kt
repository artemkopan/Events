package com.artemkopan.core.entity

data class EventEntity(
        val id: Int?,
        val address: String?,
        val provider: ProviderEntity?,
        val name: String?,
        val location: LocationEntity?,
        val hot: Boolean?,
        val photos: List<PhotoEntity>?
)

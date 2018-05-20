package com.artemkopan.core.entity

data class EventEntity(
        val id: Int?,
        val address: String?,
        val provider: ProviderEntity?,
        val name: String?,
        val hot: Boolean?,
        val thumbnail: String
)

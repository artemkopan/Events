package com.artemkopan.core.entity

data class EventEntity(
        val id: String,
        val provider: ProviderEntity?,
        val name: String?,
        val hot: Boolean?,
        val thumbnail: String?
)

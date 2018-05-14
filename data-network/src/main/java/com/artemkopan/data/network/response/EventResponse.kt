package com.artemkopan.data.network.response

import com.google.gson.annotations.SerializedName

data class EventResponse(

        @field:SerializedName("address")
        val address: String? = null,

        @field:SerializedName("provider")
        val provider: ProviderResponse? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("location")
        val location: LocationResponse? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("hot")
        val hot: Boolean? = null
)
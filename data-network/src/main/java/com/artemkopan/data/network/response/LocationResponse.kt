package com.artemkopan.data.network.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(

        @field:SerializedName("latitude")
        val latitude: Double? = null,

        @field:SerializedName("longitude")
        val longitude: Double? = null
)
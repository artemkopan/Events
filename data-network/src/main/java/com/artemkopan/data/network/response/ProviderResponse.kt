package com.artemkopan.data.network.response

import com.google.gson.annotations.SerializedName

data class ProviderResponse(

        @field:SerializedName("phone")
        val phone: PhoneResponse? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("id")
        val id: Int? = null
)
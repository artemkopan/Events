package com.artemkopan.data.network.response

import com.google.gson.annotations.SerializedName

data class VoucherResponse(

        @field:SerializedName("image")
        val image: String? = null,

        @field:SerializedName("price")
        val price: Int? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("id")
        val id: Int? = null
)
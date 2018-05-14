package com.artemkopan.data.network.response

import com.google.gson.annotations.SerializedName

data class PhoneResponse(

        @field:SerializedName("normalized_phone_number")
        val normalizedPhoneNumber: String? = null
)
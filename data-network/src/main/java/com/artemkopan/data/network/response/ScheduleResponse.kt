package com.artemkopan.data.network.response

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(

        @field:SerializedName("date_to")
        val dateTo: String? = null,

        @field:SerializedName("date_from")
        val dateFrom: String? = null,

        @field:SerializedName("time_from")
        val timeFrom: String? = null,

        @field:SerializedName("time_to")
        val timeTo: String? = null,

        @field:SerializedName("monday")
        val monday: Boolean? = null,

        @field:SerializedName("sunday")
        val sunday: Boolean? = null,

        @field:SerializedName("saturday")
        val saturday: Boolean? = null,

        @field:SerializedName("tuesday")
        val tuesday: Boolean? = null,

        @field:SerializedName("wednesday")
        val wednesday: Boolean? = null,

        @field:SerializedName("thursday")
        val thursday: Boolean? = null,

        @field:SerializedName("friday")
        val friday: Boolean? = null
)
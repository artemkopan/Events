package com.artemkopan.core.entity

data class ScheduleEntity (
        val timeFrom: String,
        val timeTo: String,
        val enabled: Boolean,
        val day: Int,
        val dayString: String
)
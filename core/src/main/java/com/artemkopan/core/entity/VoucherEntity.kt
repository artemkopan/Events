package com.artemkopan.core.entity


data class VoucherEntity(

        val id: Int,
        val price: Int,
        val description: String?,
        val image: String?
)
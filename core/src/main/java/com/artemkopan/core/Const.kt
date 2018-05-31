package com.artemkopan.core

import java.text.DecimalFormat

object Const {

    object Formatter {
        @JvmStatic
        val DECIMAL_FORMAT by lazy { DecimalFormat("#0.0") }
    }

    object Tag {
        const val NETWORK = "NETWORK"
        const val EVENT_LIST = "EVENT_LIST"
        const val CATEGORIES_LIST = "CATEGORIES_LIST"
    }

    object Url {
        const val API = "http://google.com"
    }

}
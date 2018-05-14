package com.artemkopan.core

import java.text.DecimalFormat

object Const {

    object Formatter {
        @JvmStatic
        val DECIMAL_FORMAT by lazy { DecimalFormat("#0.0") }
    }

    object Tag {
        const val NETWORK = "NETWORK"
    }

    object Url {
        const val API = "http://google.com"
    }

}
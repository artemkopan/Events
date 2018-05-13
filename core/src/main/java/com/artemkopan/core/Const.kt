package com.artemkopan.core

import java.text.DecimalFormat

object Const {

    object Formatter {
        @JvmStatic
        val DECIMAL_FORMAT by lazy { DecimalFormat("#0.0") }
    }

    object Tag {
        const val NETWORK = "NETWORK"
        const val MOVIE_LIST = "MOVIE_LIST"
    }

    object Url {
        const val API = ""
        const val TMDB_IMG = "https://image.tmdb.org/t/p/w500/%s"
        const val YOUTUBE_IMG = "http://img.youtube.com/vi/%s/0.jpg"
        const val IMDB = "http://www.omdbapi.com"
    }

}
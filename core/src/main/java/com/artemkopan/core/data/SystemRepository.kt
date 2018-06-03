package com.artemkopan.core.data

import java.util.concurrent.Executor

interface SystemRepository {

    /**
     * @return return ISO code, for example us, uk, etc.
     */
    fun getCurrentLocal(): String?

    fun getCurrentTime() : Long

    fun getCurrentTimeUTC() : Long

    fun getMainExecutor() : Executor

    fun getFetchExecutor() : Executor
}
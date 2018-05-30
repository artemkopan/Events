package com.artemkopan.core.data

interface SystemRepository {

    /**
     * @return return ISO code, for example us, uk, etc.
     */
    fun getCurrentLocal(): String?

}
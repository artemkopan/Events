package com.artemkopan.data.repo

import android.os.Build
import com.artemkopan.core.data.SystemRepository
import com.artemkopan.di.App
import javax.inject.Inject


class SystemRepositoryImpl @Inject constructor(private val app: App) : SystemRepository {
    override fun getCurrentLocal(): String? {
        @Suppress("DEPRECATION")
        return (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            app.resources().configuration.locales.get(0)
        } else {
            app.resources().configuration.locale
        }).language
    }
}
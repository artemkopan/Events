package com.artemkopan.data.repo

import android.os.Build
import android.os.Handler
import android.os.Looper
import com.artemkopan.core.data.SystemRepository
import com.artemkopan.di.App
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class SystemRepositoryImpl @Inject constructor(private val app: App) : SystemRepository {

    private val utc by lazy { TimeZone.getTimeZone("UTC") }
    private val mainExecutor by lazy { MainThreadExecutor() }
    private val fetchExecutor by lazy { Executors.newFixedThreadPool(2) }

    override fun getCurrentLocal(): String? {
        @Suppress("DEPRECATION")
        return (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            app.resources().configuration.locales.get(0)
        } else {
            app.resources().configuration.locale
        }).language
    }

    override fun getCurrentTime(): Long = System.currentTimeMillis()

    override fun getCurrentTimeUTC(): Long = Calendar.getInstance(utc).timeInMillis

    override fun getMainExecutor(): Executor = mainExecutor

    override fun getFetchExecutor(): Executor = fetchExecutor


    private class MainThreadExecutor : Executor {
        private val handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            handler.post(command)
        }
    }
}
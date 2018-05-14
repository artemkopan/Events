package com.artemkopan.events

import android.app.Application
import android.content.Context
import com.artemkopan.core.tools.Logger
import com.artemkopan.data.network.di.NetworkComponent
import com.artemkopan.di.App
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.di.component.NetworkProvider
import com.artemkopan.events.di.AppComponent
import timber.log.Timber

class EventsApp : Application(), App {


    val applicationProvider: AppComponent by lazy { AppComponent.Initializer.init(this) }

    override fun onCreate() {
        super.onCreate()
        applicationProvider.inject(this)
        AppInjector(this).registerCallbacks()
        initLogger()
    }

    override fun applicationContext(): Context = applicationContext
    override fun applicationProvider(): ApplicationProvider = applicationProvider

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Logger.addPrinter(LoggerPrinter.Timber())
        } else {
            Logger.addPrinter(LoggerPrinter.Crashlytics())
        }
    }
}

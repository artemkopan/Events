package com.artemkopan.events

import android.app.Application
import android.content.Context
import com.artemkopan.core.tools.Logger
import com.artemkopan.di.App
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.events.di.AppComponent

class EventsApp : Application(), App {

//    @Inject
//    lateinit var logger: Logger

    val appComponent: AppComponent by lazy { AppComponent.Initializer.init(this) }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        initLogger()
        Logger.DEBUG;
    }

    override fun getAppComponent(): ApplicationProvider = appComponent

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }

    private fun initLogger() {
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//            logger.addPrinter(LoggerPrinter.Timber())
//        } else {
//            logger.addPrinter(LoggerPrinter.Crashlytics())
//        }
    }
}

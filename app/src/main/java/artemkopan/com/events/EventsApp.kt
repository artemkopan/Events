package artemkopan.com.events

import android.app.Application
import android.content.Context
import artemkopan.com.core.tools.Logger
import artemkopan.com.di.App
import artemkopan.com.di.component.ApplicationProvider
import artemkopan.com.events.di.AppComponent
import timber.log.Timber
import javax.inject.Inject

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

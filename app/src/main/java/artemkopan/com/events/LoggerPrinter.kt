package artemkopan.com.events

import android.util.Log
import artemkopan.com.core.tools.Logger

interface LoggerPrinter {

    class Timber : Logger.Printer {

        override fun log(@Logger.Priority priority: Int, tag: String, message: String, t: Throwable?) {
            when (priority) {
                Logger.DEBUG -> timber.log.Timber.tag(tag).log(Log.DEBUG, t, message)
                Logger.ERROR -> timber.log.Timber.tag(tag).log(Log.ERROR, t, message)
                Logger.INFO -> timber.log.Timber.tag(tag).log(Log.INFO, t, message)
                Logger.WARN -> timber.log.Timber.tag(tag).log(Log.WARN, t, message)
                else -> timber.log.Timber.tag(tag).log(priority, t, message)
            }
        }
    }

    class Crashlytics : Logger.Printer {
        override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
            //TODO ("not implemented")
        }
    }

}

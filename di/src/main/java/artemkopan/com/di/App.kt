package artemkopan.com.di

import android.content.Context
import artemkopan.com.di.component.ApplicationProvider

interface App {
    fun getApplicationContext(): Context
    fun getAppComponent(): ApplicationProvider
}
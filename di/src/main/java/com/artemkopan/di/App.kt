package com.artemkopan.di

import android.content.Context
import com.artemkopan.di.component.ApplicationProvider

interface App {
    fun getApplicationContext(): Context
    fun getAppComponent(): ApplicationProvider
}
package com.artemkopan.di

import android.content.Context
import android.content.res.Resources
import com.artemkopan.di.component.ApplicationProvider

interface App {
    fun applicationContext(): Context
    fun applicationProvider(): ApplicationProvider
    fun resources(): Resources
}
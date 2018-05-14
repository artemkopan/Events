package com.artemkopan.di

import android.content.Context
import com.artemkopan.di.component.ApplicationProvider

interface App {
    fun applicationContext(): Context
    fun applicationProvider(): ApplicationProvider
}
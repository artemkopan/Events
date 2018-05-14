package com.artemkopan.di.component

import com.artemkopan.di.App

interface MainToolsProvider {
    fun provideContext(): App
}


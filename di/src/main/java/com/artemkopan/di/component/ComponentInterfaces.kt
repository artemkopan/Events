package com.artemkopan.di.component

import com.artemkopan.di.App


interface ApplicationProvider :
        MainToolsProvider

interface MainToolsProvider {
    fun provideContext(): App
}

package artemkopan.com.di.component

import artemkopan.com.di.App


interface ApplicationProvider :
        MainToolsProvider

interface MainToolsProvider {
    fun provideContext(): App
}

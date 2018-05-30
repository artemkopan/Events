package com.artemkopan.di.component

import com.artemkopan.core.data.SystemRepository
import com.artemkopan.core.data.events.EventsResourceRepo

interface RepositoryProvider {

    fun provideEventsResources(): EventsResourceRepo

    fun provideSystemRepository(): SystemRepository
}
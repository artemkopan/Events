package com.artemkopan.presentation.ui

import com.artemkopan.di.ActivityScope
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.presentation.ViewModelComponent
import com.artemkopan.presentation.ViewModelProvider
import dagger.Component
import dagger.Module

@ActivityScope
@Component(
        dependencies = [
            ApplicationProvider::class, ViewModelProvider::class
        ],
        modules = [EventsActivityModule::class])
interface EventsActivityComponent {
    fun inject(activity: EventsActivity)

    object Initializer {
        fun init(applicationProvider: ApplicationProvider): EventsActivityComponent {
            return DaggerEventsActivityComponent.builder()
                    .viewModelProvider(ViewModelComponent.Initializer.init(applicationProvider))
                    .applicationProvider(applicationProvider)
                    .build()
        }
    }
}

@Module
interface EventsActivityModule
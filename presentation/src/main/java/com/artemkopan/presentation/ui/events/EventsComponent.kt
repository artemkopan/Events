package com.artemkopan.presentation.ui.events

import com.artemkopan.di.ActivityScope
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.presentation.di.ViewModelComponent
import com.artemkopan.presentation.di.ViewModelProvider
import com.artemkopan.presentation.ui.events.detail.EventDetailFragment
import com.artemkopan.presentation.ui.events.list.EventListFragment
import dagger.Component
import dagger.Module

@ActivityScope
@Component(
        dependencies = [
            ApplicationProvider::class, ViewModelProvider::class
        ],
        modules = [EventsActivityModule::class])
interface EventsComponent {

    fun inject(activity: EventsActivity)
    fun inject(eventListFragment: EventListFragment)
    fun inject(eventDetailFragment: EventDetailFragment)

    object Initializer {
        @JvmStatic
        fun init(applicationProvider: ApplicationProvider): EventsComponent {
            return DaggerEventsComponent.builder()
                    .viewModelProvider(ViewModelComponent.Initializer.init(applicationProvider))
                    .applicationProvider(applicationProvider)
                    .build()
        }
    }
}

@Module
interface EventsActivityModule
package com.artemkopan.events.di

import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.di.component.MainToolsProvider
import com.artemkopan.events.EventsApp
import dagger.Component
import javax.inject.Singleton


@Component(
        dependencies = [
            MainToolsProvider::class
        ]
)
@Singleton
interface AppComponent : ApplicationProvider {

    fun inject(app: EventsApp)

    object Initializer {

        fun init(app: EventsApp): AppComponent {

            val mainToolsProvider = MainToolsComponent.Initializer
                    .init(app)

            return DaggerAppComponent.builder()
                    .mainToolsProvider(mainToolsProvider)
                    .build()
        }
    }
}


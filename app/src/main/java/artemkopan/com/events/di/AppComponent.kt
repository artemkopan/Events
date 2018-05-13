package artemkopan.com.events.di

import artemkopan.com.di.component.ApplicationProvider
import artemkopan.com.di.component.MainToolsProvider
import artemkopan.com.events.EventsApp
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


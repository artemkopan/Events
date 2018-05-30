package com.artemkopan.events.di

import com.artemkopan.data.network.di.NetworkComponent
import com.artemkopan.data.repo.di.RepositoryComponent
import com.artemkopan.di.component.*
import com.artemkopan.events.EventsApp
import dagger.Component
import javax.inject.Singleton


@Component(
        dependencies = [
            MainToolsProvider::class,
            NetworkProvider::class,
            InteractorProvider::class,
            RepositoryProvider::class
        ]
)
@Singleton
interface AppComponent : ApplicationProvider {

    fun inject(app: EventsApp)

    object Initializer {

        fun init(app: EventsApp): AppComponent {

            val mainToolsProvider = MainToolsComponent.Initializer
                    .init(app)

            val networkProvider = NetworkComponent.Initializer
                    .init(mainToolsProvider)

            val repositoryProvider = RepositoryComponent.Initializer
                    .init(mainToolsProvider)

            val interactorProvider = InteractorComponent.Initializer
                    .init(networkProvider, repositoryProvider)

            return DaggerAppComponent.builder()
                    .mainToolsProvider(mainToolsProvider)
                    .networkProvider(networkProvider)
                    .interactorProvider(interactorProvider)
                    .repositoryProvider(repositoryProvider)
                    .build()
        }
    }
}


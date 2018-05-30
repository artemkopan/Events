package com.artemkopan.events.di

import com.artemkopan.core.data.events.categories.EventCategoriesInteractor
import com.artemkopan.core.data.events.detail.EventDetailInteractor
import com.artemkopan.core.data.events.list.EventListInteractor
import com.artemkopan.core.interactor.categories.EventCategoriesInteractorImpl
import com.artemkopan.core.interactor.detail.EventDetailInteractorImpl
import com.artemkopan.core.interactor.list.EventListInteractorImpl
import com.artemkopan.di.component.InteractorProvider
import com.artemkopan.di.component.NetworkProvider
import com.artemkopan.di.component.RepositoryProvider
import dagger.Binds
import dagger.Component
import dagger.Module


@Module
interface InteractorModule {

    @Binds
    fun bindEventsInteractor(impl: EventListInteractorImpl): EventListInteractor

    @Binds
    fun bindEventDetailInteractor(impl: EventDetailInteractorImpl): EventDetailInteractor

    @Binds
    fun bindEventCategoriesInteractor(impl: EventCategoriesInteractorImpl): EventCategoriesInteractor
}

@Component(
        dependencies = [NetworkProvider::class, RepositoryProvider::class],
        modules = [InteractorModule::class]
)
interface InteractorComponent : InteractorProvider {

    object Initializer {
        @JvmStatic
        fun init(networkProvider: NetworkProvider, repositoryProvider: RepositoryProvider): InteractorProvider {
            return DaggerInteractorComponent.builder()
                    .networkProvider(networkProvider)
                    .repositoryProvider(repositoryProvider)
                    .build()
        }
    }
}

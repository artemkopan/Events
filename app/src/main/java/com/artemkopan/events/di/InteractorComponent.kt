package com.artemkopan.events.di

import com.artemkopan.core.data.events.detail.EventDetailInteractor
import com.artemkopan.core.interactor.detail.EventDetailInteractorImpl
import com.artemkopan.core.data.events.list.EventListInteractor
import com.artemkopan.core.interactor.list.EventListInteractorImpl
import com.artemkopan.di.component.InteractorProvider
import com.artemkopan.di.component.NetworkProvider
import dagger.Binds
import dagger.Component
import dagger.Module


@Module
interface InteractorModule {

    @Binds
    fun bindEventsInteractor(impl: EventListInteractorImpl): EventListInteractor

    @Binds
    fun bindEventDetailInteractor(impl: EventDetailInteractorImpl): EventDetailInteractor
}

@Component(
        dependencies = [NetworkProvider::class],
        modules = [InteractorModule::class]
)
interface InteractorComponent : InteractorProvider {

    object Initializer {
        @JvmStatic
        fun init(networkProvider: NetworkProvider): InteractorProvider {
            return DaggerInteractorComponent.builder()
                    .networkProvider(networkProvider)
                    .build()
        }
    }
}

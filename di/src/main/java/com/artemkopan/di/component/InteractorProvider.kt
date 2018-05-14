package com.artemkopan.di.component

import com.artemkopan.core.data.events.detail.EventDetailInteractor
import com.artemkopan.core.data.events.detail.EventDetailInteractorImpl
import com.artemkopan.core.data.events.list.EventListInteractor
import com.artemkopan.core.data.events.list.EventListInteractorImpl
import dagger.Binds
import dagger.Component
import dagger.Module


interface InteractorProvider {

    fun provideEventListInteractor(): EventListInteractor

    fun provideEventDetailInteractor(): EventDetailInteractor

}

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


package com.artemkopan.di.component

import com.artemkopan.core.data.events.EventsInteractor
import com.artemkopan.core.data.events.EventsInteractorImpl
import dagger.Binds
import dagger.Component
import dagger.Module


interface InteractorProvider {

    fun provideEventsInteractor(): EventsInteractor

}

@Module
interface InteractorModule {

    @Binds
    fun bindEventsInteractor(impl: EventsInteractorImpl): EventsInteractor

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


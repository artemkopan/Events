package com.artemkopan.data.network.di

import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.data.network.clients.EventsNetworkClientImpl
import com.artemkopan.data.network.clients.EventsNetworkClientMock
import com.artemkopan.data.network.clients.EventsService
import com.artemkopan.di.component.MainToolsProvider
import com.artemkopan.di.component.NetworkProvider
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
interface ClientsModule {
    @Binds
    fun bindEventsClient(impl: EventsNetworkClientMock): EventsNetworkClient

}

@Module
class ApiServiceModule {

    @Provides
    fun provideEventsService(retrofit: Retrofit) = retrofit.create(EventsService::class.java)!!

}

@Component(
        dependencies = [MainToolsProvider::class],
        modules = [ClientsModule::class, NetworkModule::class, ApiServiceModule::class])
interface NetworkComponent : NetworkProvider {

    object Initializer {
        fun init(mainToolsProvider: MainToolsProvider): NetworkProvider {
            return DaggerNetworkComponent.builder()
                    .mainToolsProvider(mainToolsProvider)
                    .build()
        }
    }

}
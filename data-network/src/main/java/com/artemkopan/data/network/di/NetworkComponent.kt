package com.artemkopan.data.network.di

import com.artemkopan.core.data.events.EventsNetworkClient
import com.artemkopan.data.network.clients.EventsNetworkClientImpl
import com.artemkopan.data.network.clients.EventsService
import com.artemkopan.di.component.MainToolsProvider
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

interface NetworkProvider {
    fun provideEventsClient(): EventsNetworkClient
}


@Module
interface ClientsModule {
    @Binds
    fun bindEventsClient(impl: EventsNetworkClientImpl): EventsNetworkClient

}

@Module
interface ApiServiceModule {

    @Provides
    fun provideEventsService(retrofit: Retrofit) = retrofit.create(EventsService::class.java)

}

@Component(
        dependencies = [MainToolsProvider::class],
        modules = [ClientsModule::class, NetworkModule::class])
interface NetworkComponent : NetworkProvider
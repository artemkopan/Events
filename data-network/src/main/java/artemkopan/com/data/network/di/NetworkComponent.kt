package artemkopan.com.data.network.di

import artemkopan.com.core.data.events.EventsNetworkClient
import artemkopan.com.data.network.clients.EventsNetworkClientImpl
import artemkopan.com.data.network.clients.EventsService
import artemkopan.com.di.component.MainToolsProvider
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
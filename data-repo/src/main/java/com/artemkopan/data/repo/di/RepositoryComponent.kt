package com.artemkopan.data.repo.di

import com.artemkopan.core.data.SystemRepository
import com.artemkopan.core.data.events.EventsResourceRepo
import com.artemkopan.data.repo.SystemRepositoryImpl
import com.artemkopan.data.repo.events.EventsResourceRepoImpl
import com.artemkopan.di.component.MainToolsProvider
import com.artemkopan.di.component.RepositoryProvider
import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Singleton


@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindEventsResources(impl: EventsResourceRepoImpl): EventsResourceRepo

    @Binds
    @Singleton
    fun bindSystemRepository(impl: SystemRepositoryImpl): SystemRepository
}


@Component(
        dependencies = [MainToolsProvider::class],
        modules = [RepositoryModule::class])
@Singleton
interface RepositoryComponent : RepositoryProvider {

    object Initializer {
        fun init(mainToolsProvider: MainToolsProvider): RepositoryProvider {
            return DaggerRepositoryComponent.builder()
                    .mainToolsProvider(mainToolsProvider)
                    .build()
        }
    }

}
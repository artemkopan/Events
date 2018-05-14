package com.artemkopan.events.di

import com.artemkopan.di.App
import com.artemkopan.di.component.MainToolsProvider
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@Module
class ToolsModule {
    @Module
    companion object {

    }
}

@Component(modules = [ToolsModule::class])
interface MainToolsComponent : MainToolsProvider {

    @Component.Builder
    interface Builder {
        fun build(): MainToolsComponent
        @BindsInstance
        fun app(app: App): Builder
    }


    object Initializer {
        fun init(app: App): MainToolsProvider =
                DaggerMainToolsComponent.builder()
                        .app(app)
                        .build()
    }

}
package artemkopan.com.events.di

import artemkopan.com.core.tools.Logger
import artemkopan.com.di.App
import artemkopan.com.di.component.MainToolsProvider
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ToolsModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        fun provideLogger(): Logger = Logger()
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


    class Initializer private constructor() {
        companion object {

        fun init(app: App): MainToolsProvider =
                DaggerMainToolsComponent.builder()
                        .app(app)
                        .build()
    }
    }

}
package com.artemkopan.presentation

import android.arch.lifecycle.ViewModel
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.presentation.base.ViewModelFactory
import com.artemkopan.presentation.ui.list.EventListViewModel
import dagger.Binds
import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.reflect.KClass

interface ViewModelProvider {

    fun provideViewModelFactory(): android.arch.lifecycle.ViewModelProvider.Factory

}

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventListViewModel::class)
    fun bindEventListViewModel(viewModel: EventListViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): android.arch.lifecycle.ViewModelProvider.Factory


}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Singleton
@Component(dependencies = [ApplicationProvider::class], modules = [ViewModelModule::class])
interface ViewModelComponent : ViewModelProvider {

    object Initializer {
        fun init(applicationProvider: ApplicationProvider): ViewModelProvider {
            return DaggerViewModelComponent.builder()
                    .applicationProvider(applicationProvider)
                    .build()
        }
    }

}



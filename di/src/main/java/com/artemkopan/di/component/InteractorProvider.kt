package com.artemkopan.di.component

import com.artemkopan.core.data.events.detail.EventDetailInteractor
import com.artemkopan.core.data.events.list.EventListInteractor


interface InteractorProvider {

    fun provideEventListInteractor(): EventListInteractor

    fun provideEventDetailInteractor(): EventDetailInteractor

}


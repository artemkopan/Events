package com.artemkopan.di.component

import com.artemkopan.core.data.events.EventsNetworkClient

interface NetworkProvider{
    fun provideEventsClient(): EventsNetworkClient
}
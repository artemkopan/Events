package com.artemkopan.data.repo.events

import com.artemkopan.core.data.events.EventsResourceRepo
import com.artemkopan.data.repo.R
import com.artemkopan.di.App
import javax.inject.Inject

class EventsResourceRepoImpl @Inject constructor(private val app: App) : EventsResourceRepo {

    override fun getUnknownCategoty(): String = app.applicationContext().getString(R.string.category_unknown)

}
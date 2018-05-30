package com.artemkopan.presentation.ui.events

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.artemkopan.core.tools.Logger
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.Injectable

class EventsActivity : AppCompatActivity(), Injectable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
    }

//    override fun onCreated(savedInstanceState: Bundle?) {
//        Logger.d("")
//    }
//
//    override fun getContentView(): Int = R.layout.activity_events
//
//    override fun getViewModelClass(): Class<EventListViewModel> = EventListViewModel::class.java

    override fun inject(appProvider: ApplicationProvider) {
        EventsComponent.Initializer.init(appProvider).inject(this)
    }

}

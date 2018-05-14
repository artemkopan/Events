package com.artemkopan.presentation.ui

import android.os.Bundle
import com.artemkopan.core.tools.Logger
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseActivity
import com.artemkopan.presentation.base.Injectable
import com.artemkopan.presentation.ui.list.EventListViewModel

class EventsActivity : BaseActivity<EventListViewModel>(), Injectable {

    override fun onCreated(savedInstanceState: Bundle?) {
        Logger.d("")
    }

    override fun getContentView(): Int = R.layout.activity_events

    override fun getViewModelClass(): Class<EventListViewModel> = EventListViewModel::class.java

    override fun inject(appProvider: ApplicationProvider) {
        EventsActivityComponent.Initializer.init(appProvider).inject(this)
    }

}

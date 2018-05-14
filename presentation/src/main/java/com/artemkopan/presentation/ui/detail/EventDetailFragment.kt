package com.artemkopan.presentation.ui.detail

import android.os.Bundle
import android.view.View
import com.artemkopan.core.tools.Logger
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseFragment
import com.artemkopan.presentation.base.Injectable
import com.artemkopan.presentation.ui.EventsComponent

class EventDetailFragment : BaseFragment<EventDetailViewModel>(), Injectable {
    override fun inject(appProvider: ApplicationProvider) {
        EventsComponent.Initializer.init(appProvider).inject(this)
    }

    override fun getContentView(): Int = R.layout.fragment_event_detail

    override fun getViewModelClass(): Class<EventDetailViewModel> = EventDetailViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d("")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
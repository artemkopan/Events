package com.artemkopan.presentation.ui.events.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import com.artemkopan.core.tools.Logger
import com.artemkopan.di.component.ApplicationProvider
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseFragment
import com.artemkopan.presentation.base.Injectable
import com.artemkopan.presentation.misc.loadImage
import com.artemkopan.presentation.ui.events.EventsComponent
import kotlinx.android.synthetic.main.fragment_event_detail.*

class EventDetailFragment : BaseFragment<EventDetailViewModel>(), Injectable {

    companion object {
        const val ARG_EVENT_ID = "ARG_EVENT_ID"
    }

    override fun inject(appProvider: ApplicationProvider) {
        EventsComponent.Initializer.init(appProvider).inject(this)
    }

    override fun getContentView(): Int = R.layout.fragment_event_detail

    override fun getViewModelClass(): Class<EventDetailViewModel> = EventDetailViewModel::class.java

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d("")
        arguments!!.getString("photo").also {
            previewImageView.loadImage(model = it)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel.loadEvent(arguments!!.getString(ARG_EVENT_ID))
    }
}
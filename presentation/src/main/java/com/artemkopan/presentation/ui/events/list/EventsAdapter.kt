package com.artemkopan.presentation.ui.events.list

import android.support.v7.util.DiffUtil
import android.view.View
import android.view.ViewGroup

import com.artemkopan.core.entity.EventEntity
import com.artemkopan.di.App
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.recycler.BaseAdapter
import com.artemkopan.presentation.base.recycler.BaseHolder
import com.artemkopan.presentation.extensions.dimen
import com.artemkopan.presentation.extensions.inflateView
import com.artemkopan.presentation.extensions.string
import com.artemkopan.presentation.misc.loadImage
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_event.*
import javax.inject.Inject

class EventsAdapter @Inject constructor(val app: App) : BaseAdapter<EventEntity, EventsAdapter.EventHolder>(DIFF_CALLBACK) {

    private val cornerRadius by lazy(LazyThreadSafetyMode.NONE) {
        app.applicationContext().dimen(R.dimen.corner_radius_small)
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return EventHolder(parent.inflateView(R.layout.item_event))
    }


    inner class EventHolder(containerView: View) : BaseHolder<EventEntity>(containerView) {

        override fun bind(item: EventEntity) {
            previewImageView.loadImage(
                    model = item.thumbnail,
                    transformations = *arrayOf(RoundedCornersTransformation(cornerRadius, 0)))

            previewImageView.transitionName = "preview_${item.id}"

            titleTextView.text = item.name
            addressTextView.text = item.address

            providerTextView.text = item.provider?.name

            //todo add implementation
            bookmarkButton.isSelected = false
            shareButton.isSelected = false
            reviewsTextView.text = app.applicationContext().string(R.string.reviews, 24)
            ratingBar.rating = 3f
        }

        override fun bindClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
            bookmarkButton.setOnClickListener(listener)
            shareButton.setOnClickListener(listener)
        }

        override fun unbindClickListener() {
            itemView.setOnClickListener(null)
            bookmarkButton.setOnClickListener(null)
            shareButton.setOnClickListener(null)
        }
    }

    companion object {

        @JvmStatic
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {

            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity) = oldItem == newItem

        }

    }

}

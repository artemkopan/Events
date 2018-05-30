package com.artemkopan.presentation.ui.events.list

import com.artemkopan.core.data.events.categories.EventCategoriesInteractor
import com.artemkopan.core.data.events.list.EventListInteractor
import com.artemkopan.core.entity.CategoryEntity
import com.artemkopan.core.entity.EventEntity
import com.artemkopan.presentation.base.BaseViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class EventListViewModel @Inject constructor(private val eventListInteractor: EventListInteractor,
                                             private val eventCategoriesInteractor: EventCategoriesInteractor)
    : BaseViewModel() {


    fun getCategories(): Single<List<CategoryEntity>> {
        return eventCategoriesInteractor.getCategories().observeOn(AndroidSchedulers.mainThread())
    }

    fun getEvents(): Single<List<EventEntity>> {
        return eventListInteractor.getEvents().observeOn(AndroidSchedulers.mainThread())
    }


}
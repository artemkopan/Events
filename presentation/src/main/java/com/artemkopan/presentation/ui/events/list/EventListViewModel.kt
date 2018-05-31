package com.artemkopan.presentation.ui.events.list

import com.artemkopan.core.data.events.categories.EventCategoriesInteractor
import com.artemkopan.core.data.events.list.EventListInteractor
import com.artemkopan.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class EventListViewModel @Inject constructor(private val eventListInteractor: EventListInteractor,
                                             private val eventCategoriesInteractor: EventCategoriesInteractor)
    : BaseViewModel() {

    init {
        clearDisposable.addAll(eventListInteractor, eventCategoriesInteractor)
        eventCategoriesInteractor.loadCategories()

        eventListInteractor.setCategoryId("10")
        eventListInteractor.loadEvents()
    }


    fun observeCategories() = eventCategoriesInteractor.observer().observeOn(AndroidSchedulers.mainThread())

    fun observeEvents(){}


}
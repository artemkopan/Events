package com.artemkopan.presentation.ui.events.list

import com.artemkopan.core.data.events.categories.EventCategoriesInteractor
import com.artemkopan.core.data.events.list.EventListInteractor
import com.artemkopan.core.tools.DataState
import com.artemkopan.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class EventListViewModel @Inject constructor(
    private val eventListInteractor: EventListInteractor,
    private val eventCategoriesInteractor: EventCategoriesInteractor
) : BaseViewModel() {

    init {
        clearDisposable.addAll(eventListInteractor, eventCategoriesInteractor)
        eventCategoriesInteractor.loadCategories()
        eventCategoriesInteractor.observer()
            .subscribe {
                if (it is DataState) {
                    it.data.forEach { (id) -> eventListInteractor.loadEvents(id) }
                }
            }
    }

    fun observeCategories() = eventCategoriesInteractor
        .observer()
        .observeOn(AndroidSchedulers.mainThread())!!

    fun observeEvents(categoryId: String) = eventListInteractor
        .observer(categoryId)
        .observeOn(AndroidSchedulers.mainThread())!!

}
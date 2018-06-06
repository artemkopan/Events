package com.artemkopan.core.data.events.categories

import com.artemkopan.core.entity.CategoryEntity
import com.artemkopan.core.states.UiState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface EventCategoriesInteractor : Disposable {

    fun observer(): Observable<UiState<List<CategoryEntity>>>

    fun loadCategories()

}
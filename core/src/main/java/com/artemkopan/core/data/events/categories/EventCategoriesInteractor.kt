package com.artemkopan.core.data.events.categories

import com.artemkopan.core.entity.CategoryEntity
import io.reactivex.Single

interface EventCategoriesInteractor {

    fun getCategories(): Single<List<CategoryEntity>>

}
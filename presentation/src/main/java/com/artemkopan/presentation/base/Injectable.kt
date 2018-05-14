package com.artemkopan.presentation.base

import com.artemkopan.di.component.ApplicationProvider


/**
 * Marks an activity / fragment injectable.
 */
interface Injectable {

    fun inject(appProvider: ApplicationProvider)

}

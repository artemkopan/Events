package com.artemkopan.presentation.extensions

import android.content.Context
import android.content.res.Resources
import androidx.annotation.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat

infix fun Context.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)
infix fun Context.dimen(@DimenRes dimenRes: Int) = resources.getDimensionPixelSize(dimenRes)
infix fun Context.string(@StringRes stringRes: Int) = resources.getString(stringRes)!!
infix fun Context.stringArray(@ArrayRes stringRes: Int): Array<out String> = resources.getStringArray(stringRes)!!
infix fun Context.drawable(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)!!

infix fun Resources.dimen(@DimenRes dimenRes: Int) = this.getDimensionPixelSize(dimenRes)
infix fun Resources.string(@StringRes stringRes: Int) = this.getString(stringRes)!!

infix fun androidx.fragment.app.Fragment.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this.context!!, colorRes)
infix fun androidx.fragment.app.Fragment.dimen(@DimenRes dimenRes: Int) = resources.getDimensionPixelSize(dimenRes)
infix fun androidx.fragment.app.Fragment.string(@StringRes stringRes: Int) = resources.getString(stringRes)!!
infix fun androidx.fragment.app.Fragment.drawable(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this.context!!, drawableRes)!!

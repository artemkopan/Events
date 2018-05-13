package com.artemkopan.presentation.extensions

import android.content.Context
import android.content.res.Resources
import android.support.annotation.*
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

infix fun Context.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)
infix fun Context.dimen(@DimenRes dimenRes: Int) = resources.getDimensionPixelSize(dimenRes)
infix fun Context.string(@StringRes stringRes: Int) = resources.getString(stringRes)!!
infix fun Context.stringArray(@ArrayRes stringRes: Int): Array<out String> = resources.getStringArray(stringRes)!!
infix fun Context.drawable(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)!!

infix fun Resources.dimen(@DimenRes dimenRes: Int) = this.getDimensionPixelSize(dimenRes)
infix fun Resources.string(@StringRes stringRes: Int) = this.getString(stringRes)!!

infix fun Fragment.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this.context!!, colorRes)
infix fun Fragment.dimen(@DimenRes dimenRes: Int) = resources.getDimensionPixelSize(dimenRes)
infix fun Fragment.string(@StringRes stringRes: Int) = resources.getString(stringRes)!!
infix fun Fragment.drawable(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this.context!!, drawableRes)!!

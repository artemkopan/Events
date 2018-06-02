package com.artemkopan.core.tools

import io.reactivex.disposables.Disposable


fun Disposable?.isDisposedSafe() = this?.isDisposed ?: true
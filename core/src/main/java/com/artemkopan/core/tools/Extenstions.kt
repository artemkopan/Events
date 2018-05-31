package com.artemkopan.core.tools

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject

fun <T> Single<T>.subscribe(subject: Subject<UiState<T>>): Disposable {
    return this
            .doOnSubscribe { subject.onNext(UiState(true)) }
            .subscribe({ subject.onNext(UiState(it)) }, { subject.onNext(UiState(it)) })
}

fun Disposable?.isDisposedSafe() = this?.isDisposed ?: true
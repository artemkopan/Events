package com.artemkopan.core.tools

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject

@kotlin.Suppress("unused")
sealed class UiState<T>

data class LoadingState<T>(val isLoading: Boolean) : UiState<T>()
data class ErrorState<T>(val throwable: Throwable) : UiState<T>()
data class DataState<T>(val data: T) : UiState<T>()


fun <T> Single<T>.subscribe(subject: Subject<UiState<T>>): Disposable {
    return this
        .doOnSubscribe { subject.onNext(LoadingState(true)) }
        .subscribe({
                       subject.onNext(LoadingState(false))
                       subject.onNext(DataState(it))
                   },
                   {
                       subject.onNext(LoadingState(false))
                       subject.onNext(ErrorState(it))
                   })
}
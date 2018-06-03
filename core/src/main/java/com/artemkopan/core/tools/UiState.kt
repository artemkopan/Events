package com.artemkopan.core.tools

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject

@kotlin.Suppress("unused")
sealed class UiState<T>

data class LoadingUiState<T>(val isLoading: Boolean) : UiState<T>()
data class ErrorUiState<T>(val throwable: Throwable) : UiState<T>()
data class DataUiState<T>(val data: T) : UiState<T>()

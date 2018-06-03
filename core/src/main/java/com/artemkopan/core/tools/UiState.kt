package com.artemkopan.core.tools

class UiState<T> {

    val isLoading: Boolean
    val isSuccess: Boolean get() = data != null
    val isError: Boolean get() = throwable != null

    var data: T? = null
        private set

    var throwable: Throwable? = null
        private set

    constructor(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    constructor(data: T?) : this(data, null)

    constructor(throwable: Throwable?) : this(null, throwable)

    constructor(data: T?, throwable: Throwable?) {
        this.isLoading = false
        this.data = data
        this.throwable = throwable
    }

    override fun toString(): String {
        return "UiState(isLoading=$isLoading, data=$data, throwable=$throwable)"
    }
}
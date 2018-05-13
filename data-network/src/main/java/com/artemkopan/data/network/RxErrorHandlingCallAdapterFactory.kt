package com.artemkopan.data.network

import com.artemkopan.core.exceptions.ApiException
import com.google.gson.Gson
import io.reactivex.*
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type


class RxErrorHandlingCallAdapterFactory private constructor(private val gson: Gson, scheduler: Scheduler) : CallAdapter.Factory() {

    private val original = RxJava2CallAdapterFactory.createWithScheduler(scheduler)

    @Suppress("UNCHECKED_CAST")
    override fun get(returnType: Type,
                     annotations: Array<Annotation>,
                     retrofit: Retrofit): CallAdapter<Any, Any>? =
            RxCallAdapterWrapper((original.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>))


    private inner class RxCallAdapterWrapper
    internal constructor(private val wrapped: CallAdapter<Any, Any>) : CallAdapter<Any, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<Any>): Any {

            val adapt = wrapped.adapt(call)

            return when (adapt) {
                is Observable<*> -> adapt.onErrorResumeNext(Function { Observable.error(parse(it, gson)) })
                is Flowable<*> -> adapt.onErrorResumeNext(Function { throwable -> Flowable.error(parse(throwable, gson)) })
                is Single<*> -> adapt.onErrorResumeNext(Function { throwable -> Single.error(parse(throwable, gson)) })
                is Maybe<*> -> adapt.onErrorResumeNext(Function { throwable -> Maybe.error(parse(throwable, gson)) })
                is Completable -> adapt.onErrorResumeNext { throwable -> Completable.error(parse(throwable, gson)) }
                else -> adapt
            }
        }
    }


    fun parse(throwable: Throwable, gson: Gson): Throwable {
        if (throwable !is HttpException) {
            return throwable
        }

        val responseBody = throwable.response().errorBody()

        if (responseBody != null) {
            //todo implement error parsing
        }

        return ApiException(throwable.message(), throwable)
    }


    companion object {

        fun create(gson: Gson, scheduler: Scheduler): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory(gson, scheduler)
        }
    }

}

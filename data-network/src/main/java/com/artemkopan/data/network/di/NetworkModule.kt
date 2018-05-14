package com.artemkopan.data.network.di

import com.artemkopan.core.Const
import com.artemkopan.core.tools.Logger
import com.artemkopan.data.network.BuildConfig
import com.artemkopan.data.network.RxErrorHandlingCallAdapterFactory
import com.artemkopan.di.App
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @JvmStatic
    @Provides
    fun provideGson() = Gson()

    @JvmStatic
    @Provides
    internal fun provideOkhttp(app: App): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(StethoInterceptor())

            val httpLogger  = HttpLoggingInterceptor.Logger { message ->
                Logger.d(Const.Tag.NETWORK, message)
            }

            clientBuilder.addNetworkInterceptor(HttpLoggingInterceptor(httpLogger)
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return clientBuilder.build()
    }

    @JvmStatic
    @Provides
    internal fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Const.Url.API)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create(gson, Schedulers.io()))
                .build()
    }

}
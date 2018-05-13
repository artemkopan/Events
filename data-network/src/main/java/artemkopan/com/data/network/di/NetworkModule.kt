package artemkopan.com.data.network.di

import artemkopan.com.core.Const
import artemkopan.com.core.tools.Logger
import artemkopan.com.data.network.BuildConfig
import artemkopan.com.data.network.RxErrorHandlingCallAdapterFactory
import artemkopan.com.di.App
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
    internal fun provideOkhttp(app: App, logger: Logger): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(StethoInterceptor())

            val httpLogger  = HttpLoggingInterceptor.Logger { message ->
                logger.d(Const.Tag.NETWORK, message)
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
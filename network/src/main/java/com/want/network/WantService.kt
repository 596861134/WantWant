package com.want.network

import com.want.network.config.CookieInterceptor
import com.want.network.config.LocalCookieJar
import com.want.network.config.LogInterceptor
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by jhb on 2020-01-13.
 */
object WantService {

    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 60L
    private const val CONNECT_TIMEOUT = 30L

    private const val BASE_URL = "https://www.wanandroid.com/"

    private var mRetrofit: Retrofit? = null

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .callTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .cookieJar(LocalCookieJar())
            .addNetworkInterceptor(CookieInterceptor())
            .addNetworkInterceptor(LogInterceptor {
                logLevel(LogInterceptor.LogLevel.BODY)
            })
            .build()

        /**
         * .addNetworkInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
        Log.d("WanService", "log: $message")
        }
        }).setLevel(HttpLoggingInterceptor.Level.BODY))
         *
         */
    }

    fun getRetrofit(): Retrofit {
        return mRetrofit ?: Retrofit.Builder()
            .baseUrl(BASE_URL)
            .validateEagerly(true)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(getClient())
            .build()
            .also { mRetrofit = it }
    }

    inline fun <reified T> create() = getRetrofit().create(T::class.java)

}
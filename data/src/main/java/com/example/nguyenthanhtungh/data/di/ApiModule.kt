package com.example.nguyenthanhtungh.data.di

import android.content.Context
import com.example.nguyenthanhtungh.data.API_KEY_PARAM
import com.example.nguyenthanhtungh.data.BASE_URL
import com.example.nguyenthanhtungh.data.BuildConfig
import com.example.nguyenthanhtungh.data.TIME_OUT
import com.example.nguyenthanhtungh.data.source.remote.network.ApiService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module(override = true) {
    single(name = "header") { createHeaderInterceptor() }
    single(name = "logging") { createLoggingInterceptor() }
    single { createOkHttpCache(get()) }
    single { initOkHttpClient(get(), get(name = "header"), get(name = "logging")) }
    single { initRetrofit(get()) }
    single { getApiService(get()) }
}

fun createOkHttpCache(context: Context): Cache {
    val size = (10 * 1024 * 1024).toLong()
    return Cache(context.cacheDir, size)
}

fun createLoggingInterceptor(): Interceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
    return logging
}

fun initOkHttpClient(cache: Cache, header: Interceptor, logging: Interceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .cache(cache)
        .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .addInterceptor(header)
        .addInterceptor(logging)
    return builder.build()
}

fun initRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun createHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        val original = chain.request()
        val newUrl = original.url().newBuilder()
            .addQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY)
            .build()
        val requestBuilder = original.newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(requestBuilder)
    }
}

fun getApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

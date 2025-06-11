package com.shaadi.di

import com.google.gson.GsonBuilder
import com.shaadi.BuildConfig
import com.shaadi.data.remote.api.NetworkService
import com.shaadi.data.remote.interceptor.FlakyApiInterceptor
import com.shaadi.data.remote.interceptor.RetryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesBaseURL(): String = "https://randomuser.me/"


    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory =
        GsonConverterFactory.create(GsonBuilder().create())

    @Provides
    @Singleton
    fun providesDataRequestInterceptor() = HttpLoggingInterceptor { message ->
        Timber.i(message)
    }.apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    @Singleton
    fun providesSupportClient(
        flakyApiInterceptor: FlakyApiInterceptor,
        dataRequestInterceptor: HttpLoggingInterceptor,
        retryInterceptor: RetryInterceptor
    ) = OkHttpClient()
        .newBuilder()
        .addInterceptor(flakyApiInterceptor)
        .addInterceptor(dataRequestInterceptor)
        .addInterceptor(retryInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun providesRetryInterceptor(): RetryInterceptor {
        return RetryInterceptor(3)
    }

    @Provides
    @Singleton
    fun providesFlakyApiInterceptor(): FlakyApiInterceptor {
        return FlakyApiInterceptor()
    }


    @Provides
    @Singleton
    fun providesRetrofitInstance(
        baseUrl: String,
        supportClient: OkHttpClient,
        converter: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converter)
        .client(supportClient)
        .build()

    @Provides
    @Singleton
    fun providesNetworkService(networkBuilder: Retrofit): NetworkService =
        networkBuilder.create(NetworkService::class.java)

}
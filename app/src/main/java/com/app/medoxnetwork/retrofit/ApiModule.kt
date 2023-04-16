package com.app.medoxnetwork.retrofit

import com.app.medoxnetwork.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


   private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val OkHttpClient = OkHttpClient.Builder().connectTimeout(15, TimeUnit.MINUTES)
            .writeTimeout(15, TimeUnit.MINUTES).readTimeout(15, TimeUnit.MINUTES)
            .connectionPool(ConnectionPool(10,5, TimeUnit.MINUTES))
            .addInterceptor(logging)

            OkHttpClient.addNetworkInterceptor { chain ->
                val original = chain.request()
                val requestBuilder =
                    original.newBuilder().addHeader("Content-Type", "application/text")
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer + SesssionManager.getInstance()?.getToken()!!")

                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()


        return OkHttpClient.build()

    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
    }

}
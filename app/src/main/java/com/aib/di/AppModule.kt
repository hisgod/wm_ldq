package com.aib.di


import android.arch.persistence.room.Room
import com.aib.BaseApplication

import com.aib.db.AppDataBase
import com.aib.net.ApiService
import com.aib.net.HeaderInterceptor
import com.blankj.utilcode.util.LogUtils

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> LogUtils.e(message) })

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(HeaderInterceptor())
                .build()

        return Retrofit.Builder()
                .baseUrl(ApiService.baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDataBase(): AppDataBase {
        return Room
                .databaseBuilder(BaseApplication.context!!, AppDataBase::class.java, "db")
                .fallbackToDestructiveMigration()
                .build()
    }
}

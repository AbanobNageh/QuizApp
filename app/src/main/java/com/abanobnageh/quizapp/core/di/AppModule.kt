package com.abanobnageh.quizapp.core.di

import com.abanobnageh.quizapp.core.network.NetworkInfo
import com.abanobnageh.quizapp.core.network.NetworkInfoImpl
import com.abanobnageh.quizapp.core.network.QuizServiceAPIBuilder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

private const val Quiz_API_URL = "http://18.191.114.87:8000/1.0.0/"

@Module
class AppModule {
  companion object {
    @Provides
    @Singleton
    fun provideGsonInstance(): Gson {
      return GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(gson: Gson): Retrofit {
      return Retrofit.Builder()
        .baseUrl(Quiz_API_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()
    }

    @Provides
    @Singleton
    fun provideNetworkInfoInstance(): NetworkInfo {
      return NetworkInfoImpl()
    }
  }
}
package com.abanobnageh.quizapp.core.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object QuizServiceAPIBuilder {
  private const val Quiz_API_URL = "http://18.191.114.87:8000/1.0.0/"
  private val gson = GsonBuilder()
    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    .create()

  private val retrofit = Retrofit.Builder()
    .baseUrl(Quiz_API_URL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addConverterFactory(ScalarsConverterFactory.create())
    .client(OkHttpClient.Builder().build())
    .build()

  fun <T> buildAPIService(serviceType: Class<T>): T {
    return retrofit.create(serviceType)
  }
}
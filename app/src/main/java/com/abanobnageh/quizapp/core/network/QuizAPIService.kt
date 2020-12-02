package com.abanobnageh.quizapp.core.network

import com.abanobnageh.quizapp.features.quiz.data.models.QuizModel
import com.abanobnageh.quizapp.features.quiz.domain.entities.Quiz
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizAPIService {
  @GET("quiz/quizzes")
  fun getQuizzes(
    @Query("page_number") pageNumber: Int,
    @Query("page_size") pageSize: Int,
    @Query("language") language: String
  ): Deferred<ArrayList<QuizModel>>

  @GET("quiz/quiz")
  fun getQuizById(
    @Query("quiz_id") pageSize: Int,
    @Query("language") language: String
  ): Deferred<QuizModel>
}
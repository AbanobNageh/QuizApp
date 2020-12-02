package com.abanobnageh.quizapp.features.quiz.domain.usecases

import com.abanobnageh.quizapp.core.error.Error
import com.abanobnageh.quizapp.core.usecase.Response
import com.abanobnageh.quizapp.core.usecase.Usecase
import com.abanobnageh.quizapp.features.quiz.domain.entities.Quiz
import com.abanobnageh.quizapp.features.quiz.domain.repositories.QuizRepository
import kotlinx.coroutines.Deferred

class GetQuizById (val quizRepository: QuizRepository): Usecase<Quiz, GetQuizByIdParams>() {
  override suspend fun call(params: GetQuizByIdParams): Deferred<Response<Error, Quiz>> {
    return quizRepository.getQuizById(params.quizId, params.language)
  }
}

class GetQuizByIdParams(
  val quizId: Int,
  val language: String
)
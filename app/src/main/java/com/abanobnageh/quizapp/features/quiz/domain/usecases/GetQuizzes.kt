package com.abanobnageh.quizapp.features.quiz.domain.usecases

import com.abanobnageh.quizapp.core.error.Error
import com.abanobnageh.quizapp.core.usecase.Response
import com.abanobnageh.quizapp.core.usecase.Usecase
import com.abanobnageh.quizapp.features.quiz.domain.entities.Quiz
import com.abanobnageh.quizapp.features.quiz.domain.repositories.QuizRepository
import kotlinx.coroutines.Deferred

class GetQuizzes(val quizRepository: QuizRepository): Usecase<ArrayList<Quiz>, GetQuizzesParams>() {
  override suspend fun call(params: GetQuizzesParams): Deferred<Response<Error, ArrayList<Quiz>>> {
    return quizRepository.getQuizzes(params.pageNumber, params.pageSize, params.language)
  }
}

class GetQuizzesParams(
  val pageNumber: Int,
  val pageSize: Int,
  val language: String
)
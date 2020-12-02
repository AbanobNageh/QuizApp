package com.abanobnageh.quizapp.features.quiz.domain.repositories

import com.abanobnageh.quizapp.core.error.Error
import com.abanobnageh.quizapp.core.usecase.Response
import com.abanobnageh.quizapp.features.quiz.domain.entities.Quiz
import kotlinx.coroutines.Deferred

abstract class QuizRepository {
  abstract suspend fun getQuizzes(pageNumber: Int, pageSize: Int, language: String): Deferred<Response<Error, ArrayList<Quiz>>>
  abstract suspend fun getQuizById(quizId: Int, language: String): Deferred<Response<Error, Quiz>>
}
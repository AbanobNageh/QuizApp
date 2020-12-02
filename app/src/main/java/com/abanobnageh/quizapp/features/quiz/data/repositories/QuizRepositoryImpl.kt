package com.abanobnageh.quizapp.features.quiz.data.repositories

import com.abanobnageh.quizapp.core.error.Error
import com.abanobnageh.quizapp.core.error.NoInternetError
import com.abanobnageh.quizapp.core.error.ServerError
import com.abanobnageh.quizapp.core.error.ServerException
import com.abanobnageh.quizapp.core.error.UnknownError
import com.abanobnageh.quizapp.core.network.NetworkInfo
import com.abanobnageh.quizapp.core.usecase.Response
import com.abanobnageh.quizapp.features.quiz.data.datasources.QuizRemoteDataSource
import com.abanobnageh.quizapp.features.quiz.domain.entities.Quiz
import com.abanobnageh.quizapp.features.quiz.domain.repositories.QuizRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(val quizRemoteDataSource: QuizRemoteDataSource, val networkInfo: NetworkInfo) : QuizRepository() {
  override suspend fun getQuizzes(pageNumber: Int, pageSize: Int, language: String): Deferred<Response<Error, ArrayList<Quiz>>> {
    if (networkInfo.internetConnected().await()) {
      try {
        val quizzes = quizRemoteDataSource.getQuizzes(pageNumber, pageSize, language).await()
        return CompletableDeferred(Response(null, quizzes))
      } catch (exception: Exception) {
        if (exception is ServerException) {
          return CompletableDeferred(Response(ServerError(exception.errorCode, exception.errorMessage), null))
        }
        else {
          return CompletableDeferred(Response(UnknownError(), null))
        }
      }
    }
    else {
      return CompletableDeferred(Response(NoInternetError(), null))
    }
  }

  override suspend fun getQuizById(quizId: Int, language: String): Deferred<Response<Error, Quiz>> {
    if (networkInfo.internetConnected().await()) {
      try {
        val quiz = quizRemoteDataSource.getQuizById(quizId, language).await()
        return CompletableDeferred(Response(null, quiz))
      } catch (exception: Exception) {
        if (exception is ServerException) {
          return CompletableDeferred(Response(ServerError(exception.errorCode, exception.errorMessage), null))
        }
        else {
          return CompletableDeferred(Response(UnknownError(), null))
        }
      }
    }
    else {
      return CompletableDeferred(Response(NoInternetError(), null))
    }
  }
}
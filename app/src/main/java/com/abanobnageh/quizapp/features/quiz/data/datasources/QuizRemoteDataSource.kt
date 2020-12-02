package com.abanobnageh.quizapp.features.quiz.data.datasources

import com.abanobnageh.quizapp.core.error.ServerException
import com.abanobnageh.quizapp.core.error.UnknownException
import com.abanobnageh.quizapp.core.network.QuizAPIService
import com.abanobnageh.quizapp.features.quiz.data.models.ErrorResponseModel
import com.abanobnageh.quizapp.features.quiz.data.models.QuizModel
import com.abanobnageh.quizapp.features.quiz.domain.entities.Quiz
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

abstract class QuizRemoteDataSource {
  abstract suspend fun getQuizzes(pageNumber: Int, pageSize: Int, language: String): Deferred<ArrayList<Quiz>>
  abstract suspend fun getQuizById(quizId: Int, language: String): Deferred<Quiz>
}

class QuizRemoteDataSourceImpl @Inject constructor(private val quizAPIService: QuizAPIService): QuizRemoteDataSource() {
  override suspend fun getQuizzes(pageNumber: Int, pageSize: Int, language: String): Deferred<ArrayList<Quiz>> {
    var response: ArrayList<QuizModel>? = null

    try {
      response = quizAPIService.getQuizzes(pageNumber, pageSize, language).await()
    } catch (throwable: Throwable) {
      if (throwable is IOException) {
        throw UnknownException()
      }
      else if (throwable is HttpException) {
        val errorJson: String? = withContext(Dispatchers.IO) { throwable.response().errorBody()?.string() }

        if (errorJson != null && errorJson.isNotEmpty()) {
          val errorResponseModel = ErrorResponseModel.fromJson(errorJson)
          throw ServerException(errorResponseModel.errorCode, errorResponseModel.errorMessage)
        }
        else {
          throw UnknownException()
        }
      }
      else {
        throw UnknownException()
      }
    }

    val quizzes: ArrayList<Quiz> = ArrayList()

    for (quizModel in response) {
      quizzes.add(quizModel.toQuiz())
    }

    return CompletableDeferred(quizzes)
  }

  override suspend fun getQuizById(quizId: Int, language: String): Deferred<Quiz> {
    var response: QuizModel? = null

    try {
      response = quizAPIService.getQuizById(quizId, language).await()
    } catch (throwable: Throwable) {
      if (throwable is IOException) {
        throw UnknownException()
      }
      else if (throwable is HttpException) {
        val errorJson: String? = withContext(Dispatchers.IO) { throwable.response().errorBody()?.string() }

        if (errorJson != null && errorJson.isNotEmpty()) {
          val errorResponseModel = ErrorResponseModel.fromJson(errorJson)
          throw ServerException(errorResponseModel.errorCode, errorResponseModel.errorMessage)
        }
        else {
          throw UnknownException()
        }
      }
      else {
        throw UnknownException()
      }
    }

    val quiz: Quiz = response.toQuiz()

    return CompletableDeferred(quiz)
  }

}
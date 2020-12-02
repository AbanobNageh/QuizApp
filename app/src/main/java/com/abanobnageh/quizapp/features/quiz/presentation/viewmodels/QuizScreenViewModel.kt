package com.abanobnageh.quizapp.features.quiz.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.abanobnageh.quizapp.core.error.Error
import com.abanobnageh.quizapp.core.usecase.Response
import com.abanobnageh.quizapp.features.quiz.domain.entities.Question
import com.abanobnageh.quizapp.features.quiz.domain.entities.Quiz
import com.abanobnageh.quizapp.features.quiz.domain.usecases.GetQuizById
import com.abanobnageh.quizapp.features.quiz.domain.usecases.GetQuizByIdParams
import com.abanobnageh.quizapp.features.quiz.domain.usecases.GetQuizzesParams
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class QuizScreenViewModel @Inject constructor(application: Application, val getQuizById: GetQuizById) : AndroidViewModel(application) {
  private val applicationContext = getApplication<Application>().applicationContext

  var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
  var quizResponse: MutableLiveData<Response<Error, Quiz>> = MutableLiveData(null)

  var currentQuestionIndex: Int = 0
  var correctQuestionCount: Int = 0
  var userAnswers: ArrayList<Int> = ArrayList()

  suspend fun getQuiz(quizId: Int) {
    isLoading.postValue(true)

    val defaultLanguage: String = Locale.getDefault().language

    val quizResponse: Response<Error, Quiz> = getQuizById.call(
      GetQuizByIdParams(
        quizId,
        defaultLanguage
      )
    ).await()

    this.isLoading.postValue(false)
    this.quizResponse.postValue(quizResponse)
  }

  fun getQuestionCount(): Int? {
    if (quizResponse.value == null || !quizResponse.value!!.isResponse()) {
      return null
    }
    else {
      if (quizResponse.value!!.response!!.questions == null || quizResponse.value!!.response!!.questions!!.isEmpty()) {
        return null
      }

      return quizResponse.value!!.response!!.questions!!.size
    }
  }

  fun hasNextQuestion(): Boolean? {
    if (quizResponse.value == null || !quizResponse.value!!.isResponse()) {
      return null
    }
    else {
      if (quizResponse.value!!.response!!.questions == null || quizResponse.value!!.response!!.questions!!.isEmpty()) {
        return null
      }

      return currentQuestionIndex + 1 != quizResponse.value!!.response!!.questions!!.size
    }
  }

  fun getCurrentQuestion(): String? {
    if (quizResponse.value == null || !quizResponse.value!!.isResponse()) {
      return null
    }
    else {
      return quizResponse.value!!.response!!.questions!![currentQuestionIndex].question
    }
  }

  fun getCurrentQuestionImage(): String? {
    if (quizResponse.value == null || !quizResponse.value!!.isResponse()) {
      return null
    }
    else {
      if (quizResponse.value!!.response!!.questions!![currentQuestionIndex].image == null) {
        return null
      }

      return quizResponse.value!!.response!!.questions!![currentQuestionIndex].image
    }
  }

  fun getCurrentQuestionAnswers(): ArrayList<String>? {
    if (quizResponse.value == null || !quizResponse.value!!.isResponse()) {
      return null
    }
    else {
      val answersArray = quizResponse.value!!.response!!.questions!![currentQuestionIndex].answers.split(" ")
      val answerArrayList = ArrayList(answersArray)

      for (index in answerArrayList.indices) {
        answerArrayList[index] = answerArrayList[index].replace("-", " ")
      }

      return answerArrayList
    }
  }

  fun setCurrentQuestionAnswer(userAnswer: Int) {
    userAnswers.add(userAnswer)
  }

  fun setNextQuestion() {
    currentQuestionIndex++
  }

  fun getCurrentQuestionCorrectAnswer(): Int? {
    if (quizResponse.value == null || !quizResponse.value!!.isResponse() || quizResponse.value!!.response!!.questions == null) {
      return null
    }
    else {
      return quizResponse.value!!.response!!.questions!![currentQuestionIndex].correctAnswer
    }
  }
}
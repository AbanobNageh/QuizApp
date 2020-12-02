package com.abanobnageh.quizapp.features.quiz.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.abanobnageh.quizapp.core.error.Error
import com.abanobnageh.quizapp.core.usecase.Response
import com.abanobnageh.quizapp.features.quiz.domain.entities.Quiz
import com.abanobnageh.quizapp.features.quiz.domain.usecases.GetQuizzes
import com.abanobnageh.quizapp.features.quiz.domain.usecases.GetQuizzesParams
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class QuizListScreenViewModel @Inject constructor(application: Application,val getQuizzes: GetQuizzes) : AndroidViewModel(application) {
  private val applicationContext = getApplication<Application>().applicationContext

  var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
  var quizResponse: MutableLiveData<Response<Error, ArrayList<Quiz>>> = MutableLiveData(null)

  suspend fun getQuizzesList() {
    isLoading.postValue(true)

    val defaultLanguage: String = Locale.getDefault().language

    val quizResponse: Response<Error, ArrayList<Quiz>> = getQuizzes.call(
      GetQuizzesParams(
        1,
        25,
        defaultLanguage
      )
    ).await()

    this.isLoading.postValue(false)
    this.quizResponse.postValue(quizResponse)
  }
}
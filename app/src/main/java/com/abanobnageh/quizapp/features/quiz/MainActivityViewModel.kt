package com.abanobnageh.quizapp.features.quiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
  private val applicationContext = getApplication<Application>().applicationContext

  var userName: String = ""
}
package com.abanobnageh.quizapp.features.quiz.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class EndScreenViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
  private val applicationContext = getApplication<Application>().applicationContext
}
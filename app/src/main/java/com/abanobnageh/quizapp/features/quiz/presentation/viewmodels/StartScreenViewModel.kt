package com.abanobnageh.quizapp.features.quiz.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.abanobnageh.quizapp.core.error.EmptyPlayerNameError
import com.abanobnageh.quizapp.core.error.Error
import com.abanobnageh.quizapp.core.usecase.NoParams
import com.abanobnageh.quizapp.core.usecase.Response
import javax.inject.Inject

class StartScreenViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
  private val applicationContext = getApplication<Application>().applicationContext

  var nameErrorEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
  var playerName: MutableLiveData<String> = MutableLiveData("")

  var startGameResponse: MutableLiveData<Response<Error, String>> = MutableLiveData(null)

  fun startGame() {
    val playerName: String? = this.playerName.value

    if (playerName == null || playerName.isEmpty()) {
      this.nameErrorEnabled.value = true
      this.startGameResponse.value = Response(EmptyPlayerNameError(), null)
      return
    }

    this.nameErrorEnabled.value = false
    this.startGameResponse.value = Response(null, playerName)
  }

}
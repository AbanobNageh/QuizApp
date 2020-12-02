package com.abanobnageh.quizapp.features.quiz.domain.entities

data class ErrorResponse(
  val errorCode: Int,
  val errorMessage: String,
)
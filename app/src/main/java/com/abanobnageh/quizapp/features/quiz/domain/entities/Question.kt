package com.abanobnageh.quizapp.features.quiz.domain.entities

data class Question(
  val answers: String,
  val correctAnswer: Int,
  val image: String?,
  val question: String,
)
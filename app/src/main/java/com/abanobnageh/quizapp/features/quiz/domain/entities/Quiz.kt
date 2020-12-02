package com.abanobnageh.quizapp.features.quiz.domain.entities

data class Quiz(
  val id: Int,
  val description: String,
  val difficulty: Int,
  val name: String,
  val rating: Int,
  val questions: ArrayList<Question>?,
)
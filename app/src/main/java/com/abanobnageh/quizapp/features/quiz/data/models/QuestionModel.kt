package com.abanobnageh.quizapp.features.quiz.data.models

import com.abanobnageh.quizapp.features.quiz.domain.entities.Question
import com.google.gson.annotations.SerializedName

data class QuestionModel(
  @SerializedName("answers")
  val answers: String,
  @SerializedName("correct_answer")
  val correctAnswer: Int,
  @SerializedName("id")
  val id: Int,
  @SerializedName("image")
  val image: String?,
  @SerializedName("question")
  val question: String,
  @SerializedName("quiz_id")
  val quizId: Int
) {
  fun toQuestion(): Question {
    return Question(answers, correctAnswer, image, question)
  }
}
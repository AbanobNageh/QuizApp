package com.abanobnageh.quizapp.features.quiz.data.models

import com.abanobnageh.quizapp.features.quiz.domain.entities.Question
import com.abanobnageh.quizapp.features.quiz.domain.entities.Quiz
import com.google.gson.annotations.SerializedName

data class QuizModel(
  @SerializedName("description")
  val description: String,
  @SerializedName("difficulty")
  val difficulty: Int,
  @SerializedName("id")
  val id: Int,
  @SerializedName("name")
  val name: String,
  @SerializedName("rating")
  val rating: Int,
  @SerializedName("questions")
  val questions: ArrayList<QuestionModel>?
) {
  fun toQuiz(): Quiz {
    var questionsList: ArrayList<Question>? = null

    if (this.questions != null) {
      questionsList = ArrayList()

      for (question in this.questions) {
        questionsList.add(question.toQuestion())
      }
    }

    return Quiz(id, description, difficulty, name, rating, questionsList)
  }
}
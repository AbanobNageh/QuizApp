package com.abanobnageh.quizapp.features.quiz.data.models

import com.abanobnageh.quizapp.features.quiz.domain.entities.ErrorResponse
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

data class ErrorResponseModel(
  @SerializedName("error_code")
  val errorCode: Int,
  @SerializedName("error_message")
  val errorMessage: String,
) {
  fun toErrorResponse(): ErrorResponse {
    return ErrorResponse(errorCode, errorMessage)
  }

  companion object ErrorResponseModelBuilder {
    fun fromJson(errorResponseJson: String): ErrorResponseModel {
      return GsonBuilder().create().fromJson(errorResponseJson, ErrorResponseModel::class.java)
    }
  }
}
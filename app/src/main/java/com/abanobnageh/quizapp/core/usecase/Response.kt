package com.abanobnageh.quizapp.core.usecase

import com.abanobnageh.quizapp.core.error.Error

class Response<Error, ResponseType>(
  var error: Error? = null,
  var response: ResponseType? = null
) {
  fun isResponse(): Boolean {
    if (this.response != null) {
      return true
    }

    return false
  }
}

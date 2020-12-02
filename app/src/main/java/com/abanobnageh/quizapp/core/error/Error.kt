package com.abanobnageh.quizapp.core.error

abstract class Error {
}

data class ServerError(
  var errorCode: Int = -1,
  var errorMessage: String = "",
) : Error() {}

class NoInternetError : Error() {}

class UnknownError : Error() {}

class EmptyPlayerNameError : Error() {}
package com.abanobnageh.quizapp.core.error

data class ServerException(val errorCode: Int, val errorMessage: String): Exception() {}

class NoInternetException: Exception() {}

class UnknownException : Exception() {}
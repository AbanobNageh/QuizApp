package com.abanobnageh.quizapp.core.usecase

import com.abanobnageh.quizapp.core.error.Error
import kotlinx.coroutines.Deferred

abstract class Usecase<ResponseType, Params> {
    abstract suspend fun call(params: Params) : Deferred<Response<Error, ResponseType>>
}

class NoParams() {}
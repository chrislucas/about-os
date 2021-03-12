package com.br.funwithcoroutines.general.htto.models

import java.lang.Exception

sealed class WrapperResult<T> {

    data class Data<T>(val result: T)

    abstract val data: Data<T>


    data class Success<T>(private val mResult: T) : WrapperResult<Result<T>>() {
        override val data: Data<Result<T>>
            get() {
                return Data(Result.success(mResult))
            }
    }


    data class Failure(private val mException: Exception) : WrapperResult<Result<Exception>>() {
        override val data: Data<Result<Exception>>
            get() = Data(Result.failure(mException))

    }
}


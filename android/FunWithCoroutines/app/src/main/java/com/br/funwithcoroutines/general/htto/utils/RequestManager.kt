package com.br.funwithcoroutines.general.htto.utils

import com.br.funwithcoroutines.general.htto.models.WrapperResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception


suspend fun <T> makeRequest(request: () -> T): WrapperResult<out Result<Any?>> {
    return withContext(Dispatchers.IO) {
        try {
            WrapperResult.Success(request())
        } catch (e: Exception) {
            WrapperResult.Failure(e)
        }
    }
}
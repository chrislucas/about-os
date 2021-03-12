package com.br.funwithcoroutines.sample.github.repository

import com.br.funwithcoroutines.general.htto.models.WrapperResult
import com.br.funwithcoroutines.general.htto.models.utils.makeRequest
import com.br.funwithcoroutines.sample.github.endpoint.GithubUserEndpoint


class GithubUserRepository(private val api: GithubUserEndpoint) {


    suspend fun requestGithubUser(): WrapperResult<out Result<Any?>> {
        return makeRequest {
            api.getUser()
        }
    }
}

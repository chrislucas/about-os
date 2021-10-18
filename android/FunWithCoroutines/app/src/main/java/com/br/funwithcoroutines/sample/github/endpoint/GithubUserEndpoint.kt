package com.br.funwithcoroutines.sample.github.endpoint

import com.br.funwithcoroutines.sample.github.models.GithubUser

interface GithubUserEndpoint {

    fun getUser(): GithubUser
}
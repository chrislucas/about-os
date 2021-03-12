package com.br.funwithcoroutines.sample.github.models

import com.google.gson.annotations.SerializedName

data class GithubUser(@SerializedName("name") val username: String)

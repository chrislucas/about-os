package com.br.funwithcoroutines.sample.github.ui

import androidx.lifecycle.MutableLiveData
import com.br.funwithcoroutines.general.ui.BaseViewModel
import com.br.funwithcoroutines.general.htto.models.WrapperResult
import com.br.funwithcoroutines.sample.github.models.BridgeViewViewModelState
import com.br.funwithcoroutines.sample.github.models.GithubUser
import com.br.funwithcoroutines.sample.github.repository.GithubUserRepository
import kotlinx.coroutines.launch

class SearchGithubRepositoriesViewModel : BaseViewModel() {

    private val mState = MutableLiveData<BridgeViewViewModelState>()
    val state = mState

    private val repository : GithubUserRepository by lazy { GithubUserRepository() }

    fun requestGithubUser(username: String) = launch {
        repository.requestGithubUser().let {
            wrapperResult: WrapperResult<Result<GithubUser>> ->
            when (wrapperResult.data.result.isSuccess){
                true -> {
                    val p = wrapperResult.data.result.getOrNull()
                }

                else -> {

                }
            }
        }
    }
}
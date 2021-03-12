package com.br.funwithcoroutines.sample.github.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.br.funwithcoroutines.R
import com.br.funwithcoroutines.sample.github.models.BridgeViewViewModelState

class SearchGithubRepositoriesFragment private constructor() : Fragment() {


    companion object {
        fun newInstance() = SearchGithubRepositoriesFragment()
    }

    private lateinit var viewModel: SearchGithubRepositoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_github_repositories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchGithubRepositoriesViewModel::class.java)
        viewModel.state.observe(this) {
            state ->
            when(state) {
                is BridgeViewViewModelState.OnSuccess<*> -> {
                    Log.i("SUCCESS", state.value.toString())
                }
                is BridgeViewViewModelState.OnError -> {
                    Log.e("ERRO", state.throwable.toString())
                }
            }
        }

        viewModel.requestGithubUser("")
    }

}
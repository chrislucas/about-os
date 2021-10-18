package com.br.funwithcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.br.funwithcoroutines.sample.github.ui.SearchGithubRepositoriesFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = SearchGithubRepositoriesFragment.newInstance()

        fragment.tag?.let {
            Log.i("TAG_FRAGMENT", it)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}
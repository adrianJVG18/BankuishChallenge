package com.adrian.bankuishcodechallenge.framework.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.adrian.bankuishcodechallenge.adapter.Output
import com.adrian.bankuishcodechallenge.databinding.ActivityHomeBinding
import com.adrian.bankuishcodechallenge.framework.utils.viewBinding
import com.adrian.bankuishcodechallenge.adapter.viewmodels.RepositoriesViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by viewBinding(ActivityHomeBinding::inflate)
    private val viewmodel: RepositoriesViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewmodel.fetchRepositories()
        observeData()
    }

    private fun observeData() {
        viewmodel.repositories.observe(this) { response ->
            when (response) {
                is Output.Success -> {
                    handleLoadingState(false)
                }
                is Output.Failure -> {
                    handleLoadingState(false)
                }
                is Output.Loading -> {
                    if (response.isLoading) {
                        handleLoadingState(true)
                    }
                }
            }
        }
    }

    private fun handleLoadingState(isLoading: Boolean) {
        binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.fragmentContainerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
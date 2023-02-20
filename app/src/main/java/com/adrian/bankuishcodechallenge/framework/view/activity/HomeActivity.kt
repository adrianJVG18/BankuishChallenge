package com.adrian.bankuishcodechallenge.framework.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
                    with(response.data[0]) {
                        val message = "$name, by $author"
                        toast(message)
                    }
                }
                is Output.Failure -> {
                    toast(response.errorMessage)
                }
            }
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
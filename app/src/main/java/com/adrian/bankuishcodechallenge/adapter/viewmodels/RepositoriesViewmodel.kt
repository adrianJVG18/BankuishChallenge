package com.adrian.bankuishcodechallenge.adapter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.bankuishcodechallenge.domain.use_case.FetchRepositoriesUsecase
import com.adrian.bankuishcodechallenge.adapter.Output
import com.adrian.bankuishcodechallenge.adapter.model.RepositoryUi
import com.adrian.bankuishcodechallenge.adapter.model.toUiModel
import com.adrian.bankuishcodechallenge.data.repository.Response
import com.adrian.bankuishcodechallenge.domain.use_case.dto.RepositoryDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RepositoriesViewmodel @Inject constructor(
    private val fetchRepositoriesUsecase: FetchRepositoriesUsecase
) :ViewModel() {

    private val _repositories = MutableLiveData<Output<List<RepositoryUi>>>(Output.Loading(false))
    val repositories: LiveData<Output<List<RepositoryUi>>> = _repositories

    fun fetchRepositories() {
        viewModelScope.launch {
            _repositories.postValue(Output.Loading(true))
            fetchRepositoriesUsecase.execute().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _repositories.postValue(Output.Success(response.data.map {
                            it.toUiModel()
                        }))
                    }
                    is Response.Failure -> {
                        _repositories.postValue(Output.Failure(response.errorMessage))
                    }
                    else -> { }
                }
            }
        }
    }

}
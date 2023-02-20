package com.adrian.bankuishcodechallenge.adapter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.bankuishcodechallenge.domain.use_case.FetchRepositoriesUsecase
import com.adrian.bankuishcodechallenge.adapter.Output
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

    private val _repositories = MutableLiveData<Output<List<RepositoryDto>>>(Output.Loading(false))
    val repositories: LiveData<Output<List<RepositoryDto>>> = _repositories

    fun fetchRepositories() {
        viewModelScope.launch {
            _repositories.postValue(Output.Loading(true))
            fetchRepositoriesUsecase.execute().collect {
                when (it) {
                    is Response.Success -> {
                        _repositories.postValue(Output.Success(it.data))
                    }
                    is Response.Failure -> {
                        _repositories.postValue(Output.Failure(it.errorMessage))
                    }
                    else -> {
                        // While waiting the request
                    }
                }
            }
        }
    }

}
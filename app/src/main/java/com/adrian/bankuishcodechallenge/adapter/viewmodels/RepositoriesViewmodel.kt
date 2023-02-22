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
import com.adrian.bankuishcodechallenge.domain.use_case.dto.RequestRepositoriesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RepositoriesViewmodel @Inject constructor(
    private val fetchRepositoriesUsecase: FetchRepositoriesUsecase
) : ViewModel() {

    val query = MutableLiveData("")

    private val _repositories = MutableLiveData<Output<List<RepositoryUi>>>(Output.Loading(false))
    val repositories: LiveData<Output<List<RepositoryUi>>> = _repositories

    private val _totalRepositories = MutableLiveData(0)
    val totalRepositories: LiveData<Int> = _totalRepositories

    fun fetchRepositories(currentPage: Int? = 1) {
        viewModelScope.launch {
            _repositories.postValue(Output.Loading(true))
            val request = RequestRepositoriesDto(query.value, currentPage)
            fetchRepositoriesUsecase.execute(request).collect { response ->
                when (response) {
                    is Response.Success -> {
                        _repositories.postValue(Output.Success(response.data.items.map {
                            it.toUiModel()
                        }))
                        _totalRepositories.postValue(response.data.count)
                    }
                    is Response.Failure -> {
                        _repositories.postValue(Output.Failure(response.errorMessage))
                    }
                    else -> {}
                }
            }
        }
    }

}
package com.adrian.bankuishcodechallenge.domain.use_case

import androidx.compose.ui.text.toLowerCase
import com.adrian.bankuishcodechallenge.data.repository.RepositoryApi
import com.adrian.bankuishcodechallenge.data.repository.Response
import com.adrian.bankuishcodechallenge.domain.use_case.dto.RepositoryDto
import com.adrian.bankuishcodechallenge.data.repository.toDto
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@Module
@InstallIn(ViewModelComponent::class)
class FetchRepositoriesUsecase @Inject constructor(
    private val repositoriesApi: RepositoryApi
) : FlowUsecase<Int, Response<List<RepositoryDto>>>() {

    companion object {
        const val QUERY_PARAM = "language:Kotlin"
        const val PER_PAGE_PARAM = 20
    }

    override suspend fun execute(params: Int?): Flow<Response<List<RepositoryDto>>> = flow {
        repositoriesApi.getRepositories(QUERY_PARAM, PER_PAGE_PARAM, params ?: 1).collect { response ->
            when (response) {
                is Response.Success -> {
                    emit(Response.Success(response.data.items
                        .map { it.toDto() }))
                }
                is Response.Failure -> {
                    emit(Response.Failure(response.errorMessage))
                }
                else -> { }
            }
        }

    }
}
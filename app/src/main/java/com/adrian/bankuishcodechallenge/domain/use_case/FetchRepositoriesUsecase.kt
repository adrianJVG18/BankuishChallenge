package com.adrian.bankuishcodechallenge.domain.use_case

import com.adrian.bankuishcodechallenge.data.repository.RepositoryApi
import com.adrian.bankuishcodechallenge.data.repository.Response
import com.adrian.bankuishcodechallenge.data.repository.toDto
import com.adrian.bankuishcodechallenge.domain.use_case.dto.RepositoriesResponseDto
import com.adrian.bankuishcodechallenge.domain.use_case.dto.RequestRepositoriesDto
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
) : FlowUsecase<RequestRepositoriesDto, Response<RepositoriesResponseDto>>() {

    companion object {
        const val DEFAULT_QUERY_PARAM = "language:Kotlin"
        const val PER_PAGE_PARAM = 20
        const val DEFAULT_CURRENT_PAGE = 1
    }

    override suspend fun execute(params: RequestRepositoriesDto?):
            Flow<Response<RepositoriesResponseDto>> = flow {

        repositoriesApi.getRepositories(
            query = getQueryParam(params?.query),
            perPage = PER_PAGE_PARAM,
            page = params?.page ?: DEFAULT_CURRENT_PAGE
        ).collect { response ->
            when (response) {
                is Response.Success -> {
                    emit(Response.Success(response.data.toDto()))
                }
                is Response.Failure -> {
                    emit(Response.Failure(response.errorMessage))
                }
                else -> {}
            }
        }
    }

    private fun getQueryParam(query: String? = ""): String {
        if (query.isNullOrBlank()) return DEFAULT_QUERY_PARAM
        return query
    }
}
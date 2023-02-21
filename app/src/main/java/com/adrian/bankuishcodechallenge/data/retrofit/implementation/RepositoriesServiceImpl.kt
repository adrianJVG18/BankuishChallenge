package com.adrian.bankuishcodechallenge.data.retrofit.implementation

import com.adrian.bankuishcodechallenge.data.repository.RepositoryApi
import com.adrian.bankuishcodechallenge.data.repository.Response
import com.adrian.bankuishcodechallenge.data.retrofit.service.RepositoriesService
import com.adrian.bankuishcodechallenge.entities.RepositoriesResponse
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesServiceImpl @Inject constructor(
    private val repositoriesApi: RepositoriesService
) : RepositoryApi {

    override suspend fun getRepositories(query: String, perPage: Int, page: Int)
            : Flow<Response<RepositoriesResponse>> = flow {
        emit(Response.Loading(true))
        emit(
            Response.Success(
                repositoriesApi.getRepositories(
                    query = query,
                    perPage = perPage,
                    page = page
                )
            )
        )
    }.catch { e ->
        emit(Response.Failure(e.message ?: "Unknown error while getting repositories"))
    }.flowOn(Dispatchers.IO)
}
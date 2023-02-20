package com.adrian.bankuishcodechallenge.data.repository

import com.adrian.bankuishcodechallenge.entities.RepositoriesResponse
import kotlinx.coroutines.flow.Flow


interface RepositoryApi {

    suspend fun getRepositories(
        query: String,
        perPage: Int,
        page: Int
    ): Flow<Response<RepositoriesResponse>>
}
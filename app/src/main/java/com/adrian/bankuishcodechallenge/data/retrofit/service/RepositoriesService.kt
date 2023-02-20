package com.adrian.bankuishcodechallenge.data.retrofit.service

import com.adrian.bankuishcodechallenge.entities.RepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoriesService {

    @GET("search/repositories?")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): RepositoriesResponse

}
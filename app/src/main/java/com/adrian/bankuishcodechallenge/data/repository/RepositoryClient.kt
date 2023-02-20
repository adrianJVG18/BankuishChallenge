package com.adrian.bankuishcodechallenge.data.repository

import com.adrian.bankuishcodechallenge.data.retrofit.implementation.RepositoriesServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryClient {

    @Provides
    fun provideRepositoryApi(
        repositoriesServiceImpl: RepositoriesServiceImpl
    ): RepositoryApi = repositoriesServiceImpl

}
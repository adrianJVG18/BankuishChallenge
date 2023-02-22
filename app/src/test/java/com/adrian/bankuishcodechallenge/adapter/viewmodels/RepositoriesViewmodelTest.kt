package com.adrian.bankuishcodechallenge.adapter.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adrian.bankuishcodechallenge.adapter.Output
import com.adrian.bankuishcodechallenge.adapter.model.toUiModel
import com.adrian.bankuishcodechallenge.data.repository.Response
import com.adrian.bankuishcodechallenge.domain.use_case.FetchRepositoriesUsecase
import com.adrian.bankuishcodechallenge.domain.use_case.dto.RepositoryDto
import com.adrian.bankuishcodechallenge.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class RepositoriesViewmodelTest {

    companion object {
        private val REPOSITORY_1 = RepositoryDto(name = "project 1", author =  "alfa")
        private val REPOSITORY_2 = RepositoryDto(name = "project 2", author =  "beta")
        private val REPOSITORIES = arrayListOf(
            REPOSITORY_1,
            REPOSITORY_2
        )
        private val ERROR_MESSAGE = "Failed to retrieve Repositories"
    }

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    private lateinit var viewmodel: RepositoriesViewmodel

    @Mock
    private lateinit var fetchRepositoriesUsecase: FetchRepositoriesUsecase


    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(RepositoriesViewmodelTest::class)
        viewmodel = RepositoriesViewmodel(fetchRepositoriesUsecase)
    }

    @Test
    fun `when fetchRepositories then fetchRepositoriesUsecase is executed`() = runTest {
        testCoroutineRule.runBlockingTest {
            viewmodel.fetchRepositories()
            verify(fetchRepositoriesUsecase).execute()
        }
    }

    @Test
    fun `when fetchRepositories and fetchRepositoriesUsecase succeeds then emit list of RepositoriesDto`() =
        runTest {
            testCoroutineRule.runBlockingTest {
                val flowable = flow<Response<List<RepositoryDto>>> {
                    emit(Response.Success(REPOSITORIES))
                }
                doReturn(flowable)
                    .`when`(fetchRepositoriesUsecase).execute()

                viewmodel.fetchRepositories()
                assert(viewmodel.repositories.value == Output.Success(REPOSITORIES.map { it.toUiModel() }))
            }
        }

    @Test
    fun `when fetchRepositories and fetchRepositoriesUsecase fails then emit errorMessage`() =
        runTest {
            testCoroutineRule.runBlockingTest {
                val flowable = flow<Response<List<RepositoryDto>>> {
                    emit(Response.Failure(ERROR_MESSAGE))
                }
                doReturn(flowable)
                    .`when`(fetchRepositoriesUsecase).execute()

                viewmodel.fetchRepositories()
                assert(viewmodel.repositories.value is Output.Failure)
                assert((viewmodel.repositories.value as Output.Failure).errorMessage == ERROR_MESSAGE)
            }
        }

}
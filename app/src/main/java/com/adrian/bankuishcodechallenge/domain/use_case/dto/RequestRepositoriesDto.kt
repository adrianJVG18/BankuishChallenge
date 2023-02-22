package com.adrian.bankuishcodechallenge.domain.use_case.dto

data class RequestRepositoriesDto(
    val query: String? = null,
    val page: Int? = null
)
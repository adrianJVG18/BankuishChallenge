package com.adrian.bankuishcodechallenge.domain.use_case.dto

data class RepositoriesResponseDto(
    val count: Int,
    val incompleteResults: Boolean,
    val items: List<RepositoryDto>
)
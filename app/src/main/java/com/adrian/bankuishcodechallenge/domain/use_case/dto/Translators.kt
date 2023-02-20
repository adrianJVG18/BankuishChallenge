package com.adrian.bankuishcodechallenge.domain.use_case.dto

import com.adrian.bankuishcodechallenge.entities.Repository

fun Repository.toDto(): RepositoryDto {
    return with(this) {
        RepositoryDto(
            name = name ?: "",
            author = owner?.login ?: ""
        )
    }
}

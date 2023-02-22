package com.adrian.bankuishcodechallenge.data.repository

import com.adrian.bankuishcodechallenge.domain.use_case.dto.RepositoryDto
import com.adrian.bankuishcodechallenge.domain.use_case.dto.RepositoriesResponseDto
import com.adrian.bankuishcodechallenge.entities.RepositoriesResponse
import com.adrian.bankuishcodechallenge.entities.Repository
import com.google.gson.annotations.SerializedName

fun Repository.toDto(): RepositoryDto {
    return with(this) {
        RepositoryDto(
            id = id ?: 0,
            name = name ?: "",
            author = owner?.login ?: "",
            avatarUrl = owner?.avatarUrl ?: "",
            ownerUrl = owner?.url ?: "",
            description = description ?: "",
            creationDate = createdAt ?: "",
            updateDate = updatedAt ?: "",
            issues = openIssues ?: 0,
            watchers = watchers ?: 0
        )
    }
}

fun RepositoriesResponse.toDto() : RepositoriesResponseDto {
    return RepositoriesResponseDto(
        count = this.count,
        incompleteResults = this.incompleteResults,
        items = this.items.map { it.toDto() }
    )
}

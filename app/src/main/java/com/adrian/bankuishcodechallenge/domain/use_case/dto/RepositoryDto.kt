package com.adrian.bankuishcodechallenge.domain.use_case.dto

import android.os.Bundle

data class RepositoryDto(
    val id: Int = 0,
    val name: String = "",
    val author: String = "",
    val avatarUrl: String = "",
    val ownerUrl: String = "",
    val description: String = "",
    val creationDate: String = "",
    val updateDate: String = "",
    val issues: Int = 0,
    val watchers: Int = 0
)
package com.adrian.bankuishcodechallenge.adapter.model

import com.adrian.bankuishcodechallenge.domain.use_case.dto.RepositoryDto

fun RepositoryDto.toUiModel(): RepositoryUi =
    RepositoryUi(
        id, name, author, avatarUrl, ownerUrl, description, creationDate, updateDate, issues, watchers
    )
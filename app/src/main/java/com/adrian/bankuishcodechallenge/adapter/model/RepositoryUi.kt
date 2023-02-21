package com.adrian.bankuishcodechallenge.adapter.model

import android.os.Bundle

data class RepositoryUi(
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
) {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val AUTHOR = "author"
        const val AVATAR_URL = "avatar url"
        const val OWNER_URL = "onwer url"
        const val DESCRIPTION = "description"
        const val CREATION_DATE = "creation date"
        const val UPDATED_DATE = "updated date"
        const val ISSUES = "issues count"
        const val WATCHERS = "watchers count"
    }
    fun toBundle() : Bundle = Bundle().apply {
        putInt(ID, id)
        putString(NAME, name)
        putString(AUTHOR, author)
        putString(AVATAR_URL, avatarUrl)
        putString(OWNER_URL, ownerUrl)
        putString(DESCRIPTION, description)
        putString(CREATION_DATE, creationDate)
        putString(UPDATED_DATE, updateDate)
        putInt(ISSUES, issues)
        putInt(WATCHERS, watchers)
    }

}
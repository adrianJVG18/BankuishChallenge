package com.adrian.bankuishcodechallenge.entities

import com.google.gson.annotations.SerializedName

data class RepositoriesResponse(
    @SerializedName("total_count")
    val count: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Repository>
)
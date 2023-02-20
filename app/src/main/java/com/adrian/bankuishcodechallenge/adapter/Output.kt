package com.adrian.bankuishcodechallenge.adapter

sealed class Output<T> {
    data class Loading<T>(val isLoading: Boolean) : Output<T>()
    data class Success<T>(val data: T) : Output<T>()
    data class Failure<T>(val errorMessage: String) : Output<T>()
}
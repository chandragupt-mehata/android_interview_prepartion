package com.example.myapplication.mvvmone.common

sealed class ResourceOne<T>(val data: T?, val msg: String?, val errorMsg: String?) {

    data class Success<T>(val result: T): ResourceOne<T>(result, null, null)
    data class Error<T>(val error: String): ResourceOne<T>(null, null, error)
    data class Loading<T>(val isLoading: Boolean): ResourceOne<T>(null, null, null)

}

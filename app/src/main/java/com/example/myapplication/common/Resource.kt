package com.example.myapplication.common

sealed class Resource<T>(val t: T?) {

    data class Success<T>(val data: T): Resource<T>(data)
    data class Failure<T>(val errorMsg: String?): Resource<T>(null)
    data class Loading<T>(val isLoading: Boolean): Resource<T>(null)

}
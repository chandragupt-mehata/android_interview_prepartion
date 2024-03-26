package com.example.myapplication.mvvmone.data.network

import com.example.myapplication.mvvmone.data.model.ResultDtoOne
import retrofit2.http.GET

interface ApIInterfaceOne {

    companion object {
        const val BASE_URL = "https://dog.ceo/api/breeds"
    }

    @GET("/list/all")
    suspend fun getResultList(): ResultDtoOne

}
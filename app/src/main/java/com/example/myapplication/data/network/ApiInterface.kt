package com.example.myapplication.data.network

import com.example.myapplication.data.model.ResultDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * https://jsonmock.hackerrank.com/api/articles?author=coloneltcb&page=1
 */
interface ApiInterface {

    @GET("/api/articles")
    suspend fun getResult(@Query("author") author: String, @Query("page") pageNumber: Int): ResultDto

    @GET("/api/articles")
    fun getPlainResult(@Query("author") author: String, @Query("page") pageNumber: Int): Call<ResultDto>

    companion object {
        const val BASE_URL = "https://jsonmock.hackerrank.com"
    }

}
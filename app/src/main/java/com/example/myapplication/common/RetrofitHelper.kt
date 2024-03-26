package com.example.myapplication.common

import com.example.myapplication.data.network.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun getApiInterface(): ApiInterface {
        return getInstance().create(ApiInterface::class.java)
    }

}
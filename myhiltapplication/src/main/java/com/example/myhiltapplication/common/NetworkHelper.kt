package com.example.myhiltapplication.common

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {

    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("").addConverterFactory(GsonConverterFactory.create()).build()
    }

}



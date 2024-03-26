package com.example.myapplication.mvvmone.common

import com.example.myapplication.mvvmone.data.network.ApIInterfaceOne
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelperOne {

    @Synchronized
    fun getRetrofitInstance(): ApIInterfaceOne {
        return Retrofit.Builder().baseUrl(ApIInterfaceOne.BASE_URL).
        addConverterFactory(GsonConverterFactory.create()).build().create(ApIInterfaceOne::class.java)
    }

}
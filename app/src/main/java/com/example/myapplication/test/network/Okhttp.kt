package com.example.myapplication.test.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * https://guides.codepath.com/android/Using-OkHttp
 */
fun main() {
    doCall()
}

fun doCall() {
    runBlocking {
       launch(Dispatchers.IO) {
           val client = OkHttpClient()
           val request = Request.Builder().url("https://api.publicapis.org/entries").build()

           val requestBuilder = HttpUrl.parse("https://api.publicapis.org/entries")?.newBuilder()?.also {
               //it.addQueryParameter()
           }
           val newUrl = requestBuilder?.build().toString()
           val newRequest = Request.Builder().url(newUrl).build()

           /*val urlBuilder =
               HttpUrl.parse("https://ajax.googleapis.com/ajax/services/search/images")!!
                   .newBuilder()
           urlBuilder.addQueryParameter("v", "1.0")
           urlBuilder.addQueryParameter("q", "android")
           urlBuilder.addQueryParameter("rsz", "8")
           val url = urlBuilder.build().toString()

           val request1 = Request.Builder()
               .url(url)
               .build()*/

           val response = client.newCall(request).execute()
           println("response is: ${response.body().toString()}")
       }
    }
}
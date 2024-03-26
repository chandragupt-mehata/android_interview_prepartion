package com.example.myapplication.test

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

fun main() {
    runBlocking {
        val customData = "Custom Data"
        //val contextWithCustomData = coroutineContext + CustomDataKey(customData)

        launch() {

        }
    }
}

class CustomDataKey(private val data: String)


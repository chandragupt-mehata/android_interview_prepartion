package com.example.myapplication.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch(Dispatchers.IO) {
            returnFlow().collect {
                println("inside collector value: $it and thread name: ${Thread.currentThread().name}")
            }
        }
    }
}

fun returnFlow() = flow {
    repeat(10) {
        kotlinx.coroutines.delay(100)
        println("inside emitter: ${Thread.currentThread().name}")
        emit(it)
    }
}
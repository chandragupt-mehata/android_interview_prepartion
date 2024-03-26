package com.example.myapplication.flow

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.Exception

fun main() {
    runBlocking {
        try {
            emitData().collect {
                println("value inside collector is: $it")
            }
        } catch (e: Exception) {
            println("exception caught inside collector is: ${e.message}")
        }
    }
}

fun emitData() = flow<Int> {
    throw Exception("inside emmiter")
    emit(5)
}

fun <T> wrapper() = flow<T> {

}
package com.example.myapplication.coroutine

import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        doIt()
    }
}

suspend fun doIt() {
    println("before")
    //delay(1000)
    println("after")
}
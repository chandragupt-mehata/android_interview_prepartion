package com.example.myapplication.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.Exception

fun main() {
    /*runBlocking {
        val scope = CoroutineScope(Job())
        scope.launch {
            try {
                coroutineScope {
                    launch(Dispatchers.Default) {
                        delay(1000)
                        throw Exception("throw")
                        println("inside 1")
                    }
                    launch {
                        delay(2000)
                        println("inside 2")
                    }
                }
            } catch (ex: Exception) {
                println("exception is: $ex")
            }
        }.join()
    }*/

    //case two to verify withContext
    runBlocking {
        val scope = CoroutineScope(Job())
        scope.launch {
            launch {
                try {
                    withContext(Dispatchers.IO) {
                        delay(1000)
                        throw Exception("throw")
                        println("inside 1")
                    }
                } catch (exception: Exception) {
                    println("exception is: $exception")
                }
            }
            launch {
                delay(2000)
                println("inside 2")
            }
        }.join()
    }
}
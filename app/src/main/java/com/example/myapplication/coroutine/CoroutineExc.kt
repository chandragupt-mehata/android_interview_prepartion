package com.example.myapplication.coroutine

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import java.lang.RuntimeException
import java.util.concurrent.Executors

fun main() {
    val executer = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
    throwCoroutineException()
}

private fun throwCoroutineException() {
    runBlocking {
        launch() {
            supervisorScope {
                coroutineScope {
                    launch {
                        repeat(10) {
                            delay(100)
                            println("child 1: $it")
                        }
                    }
                    launch {
                        repeat(10) {
                            delay(100)
                            println("child 2: $it")
                        }
                    }
                    launch {
                        delay(500)
                        println("child 3: ")
                        throw RuntimeException()
                    }
                }
            }
            repeat(10) {
                delay(100)
                println("parent: $it")
            }
        }
    }
    println("parent: end")
}
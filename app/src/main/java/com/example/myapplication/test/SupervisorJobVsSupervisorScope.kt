package com.example.myapplication.test

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import java.lang.RuntimeException

fun main() {
    runBlocking {
        val exceptionHandler = CoroutineExceptionHandler {_, exception ->
            println("exception is: ${exception.message}")
        }
        val superVisorJob = Job(SupervisorJob())
        val job = CoroutineScope(Dispatchers.IO).launch {
            supervisorScope {
                launch(exceptionHandler) {
                    delay(1000)
                    throw RuntimeException("throw it")
                }
                launch {
                    repeat(5) {
                        delay(500)
                        println("inside child 2: $it")
                    }
                }
            }
        }
        job.join()
    }
}
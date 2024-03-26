package com.example.myapplication.coroutine

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun firstSuspension() {
    println("before")
    suspendCoroutine {
        thread {
            println("suspension starts")
            Thread.sleep(1000)
            it.resume(Unit)
            println("suspension ends")
        }
    }
    println("after")
}

fun main() {
    runBlocking {
        simulateSuspendWithRespectToCoroutine()
    }
}

/**
 * although first coroutine suspend its underlying coroutine for infinite time it wont block underlying thread
 * second coroutine keep on running
 */
suspend fun simulateSuspendWithRespectToCoroutine() {
    var continuation: Continuation<String>? = null
    coroutineScope {
        val executors = Executors.newSingleThreadExecutor()
        launch(executors.asCoroutineDispatcher()) {
            //first coroutine
            println("simulateSuspendWithRespectToCoroutine: first coroutine name: ${Thread.currentThread().name}")
            suspendCoroutine<String> {
                continuation = it
                println("simulateSuspendWithRespectToCoroutine: inside first suspendCoroutine")
            }
            println("simulateSuspendWithRespectToCoroutine: end of first coroutine")
        }
        launch(executors.asCoroutineDispatcher()) {
            //second coroutine
            println("simulateSuspendWithRespectToCoroutine: second coroutine name: ${Thread.currentThread().name}")
            delay(5000)
            continuation?.resume("I am back")
            println("simulateSuspendWithRespectToCoroutine: end of second coroutine")
        }
        delay(2000)
        println("simulateSuspendWithRespectToCoroutine: end of coroutine scope")
    }
    println("simulateSuspendWithRespectToCoroutine: all end")
}
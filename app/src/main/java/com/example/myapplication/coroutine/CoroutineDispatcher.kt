package com.example.myapplication.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * https://write.agrevolution.in/kotlin-coroutines-part-4-cancellation-811cd5a9813a
 */
fun main2cc() {
    runBlocking {
        launch(Dispatchers.IO) {
            launch {
                repeat(5) {
                    delay(100)
                    println("inside first: $it")
                }
            }
            launch(Dispatchers.Unconfined) {
                //if you will use some suspend function like within repeat then it will work concurrently
                //with other coroutine but ofcourse it will start immediately
                println("inside unconfined: ${Thread.currentThread().name}")
            }
            println("all end")
        }
    }
}

fun mainccc() {
    runBlocking {
        launch(newFixedThreadPoolContext(10, "hii")) {
            for (i in 1..10) {
                launch {
                    println("hello newFixedThreadPoolContext thread name: ${Thread.currentThread().name}")
                }
            }
        }
        launch(newSingleThreadContext("hii")) {
            for (i in 1..10) {
                launch {
                    println("hello newSingleThreadContext thread name: ${Thread.currentThread().name}")
                }
            }
        }
    }
}

fun main() {
    val executors = Executors.newFixedThreadPool(10)
    val cached = Executors.newCachedThreadPool()
    val scope = CoroutineScope(Job())
    runBlocking {
        val job = scope.launch {
            launch(executors.asCoroutineDispatcher()) {
                for (i in 1..10) {
                    delay(100)
                    launch {
                        println("hello newFixedThreadPool thread name: ${Thread.currentThread().name}")
                    }
                }
            }.invokeOnCompletion {
                println("completed")
            }
            launch(cached.asCoroutineDispatcher()) {
                for (i in 1..10) {
                    delay(100)
                    launch {
                        println("hello newCachedThreadPool thread name: ${Thread.currentThread().name}")
                    }
                }
            }.invokeOnCompletion {
                println("completed cached one")
            }
            delay(200)
            println("cancelling it")
            scope.cancel()
        }.join()
    }
    executors.shutdown()
    cached.shutdown()
}

suspend fun Call.suspendableAwait() {
    return suspendCoroutine {
            continuation ->
        //enqueue()
    }
}

suspend fun Call.await(): Response = suspendCancellableCoroutine { continuation ->
    enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            continuation.resume(response)
        }

        override fun onFailure(call: Call, e: IOException) {
            continuation.resumeWithException(e)
        }
    })
    continuation.invokeOnCancellation {
        try {
            cancel()
        } catch (ignore: Throwable) {
        }
    }
}

/*
fun suspendCancellableCoroutine { continuations ->
    convertBitmap(bitmap) { result, throwable ->
        if(result != null) { continuation.resume(result)}
    } else {
        continuation.resumeWithException(throwable)
    }
}*/

package com.example.myapplication.coroutine

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun fetchData(): String {
    return suspendCancellableCoroutine { continuation: Continuation<String> ->
        // Simulating an asynchronous operation with a callback
        fetchFromNetwork { result, error ->
            if (error != null) {
                continuation.resumeWithException(error)
            } else {
                continuation.resume(result!!)
            }
        }
    }
}

fun higherOrderFun(func: () -> String): String {
    return func()
}

fun fetchFromNetwork(callback: (String?, Throwable?) -> Unit) {
    // Simulating an asynchronous operation
    val success = true // Set to false to simulate an error
    if (success) {
        callback("Fetched data", null)
    } else {
        callback(null, RuntimeException("Failed to fetch data"))
    }
}

suspend fun main() {
    try {
        val data = fetchData()
        println("Received data: $data")
    } catch (e: Throwable) {
        println("Error: ${e.message}")
    }
}


// Define a simple wrapper class to hold the result and error
/*data class ResultOrError<T>(val result: T?, val error: Throwable?)

// Higher-order function with callback
fun <T> higherOrderFunction(callback: (ResultOrError<T>) -> Unit) {
    // Simulating an asynchronous operation
    val success = true // Set to false to simulate an error
    if (success) {
        val resultOrError: ResultOrError<T> = ResultOrError("Fetched data", null)
        callback(resultOrError)
    } else {
        callback(ResultOrError(null, RuntimeException("Failed to fetch data")))
    }
}

// Wrapper function without using suspendCoroutine
suspend fun <T> asyncHigherOrderFunction(): T {
    lateinit var resultOrError: ResultOrError<T>
    val job = runBlocking {
        higherOrderFunction { callbackResultOrError ->
            resultOrError = callbackResultOrError
        }
    }

    if (resultOrError.error != null) {
        throw resultOrError.error
    } else {
        @Suppress("UNCHECKED_CAST")
        return resultOrError.result as T
    }
}

fun mainCustom() {
    // Now, you can call asyncHigherOrderFunction like a regular function
    try {
        val result = runBlocking {
            asyncHigherOrderFunction<String>()
        }
        println("Result: $result")
    } catch (e: Throwable) {
        println("Error: $e")
    }
}*/


package com.example.myapplication.coroutine

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.system.measureTimeMillis

suspend fun fetchData(): String {
    println("fetching data")
    return suspendCancellableCoroutine { continuation: Continuation<String> ->
        // Simulating an asynchronous operation with a callback
        println("inside suspendCancellableCoroutine")
        fetchFromNetwork { result, error ->
            println("returning final result through continuation object")
            if (error != null) {
                continuation.resumeWithException(error)
            } else {
                continuation.resume(result!!)
            }
        }
        println("end of suspendCancellableCoroutine")
    }
}

fun higherOrderFun(func: () -> String): String {
    return func()
}

fun fetchFromNetwork(callback: (String?, Throwable?) -> Unit) {
    // Simulating an asynchronous operation
    val success = true // Set to false to simulate an error
    println("inside fetchFromNetwork returning result through callback")
    if (success) {
        callback("Fetched data", null)
    } else {
        callback(null, RuntimeException("Failed to fetch data"))
    }
}

suspend fun mainCustomSuspend() {
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

/**
 * coroutine is basically designed to perform a asynchronous task in a synchronous way without blocking the
 * underlying thread
 */
suspend fun main() {
    runBlocking {
        val time1 = measureTimeMillis {
            //doAsyncWork()  // not synchronized
            //doAsyncWorkIndependent() // not synchronized
            //doAsyncWorkIndependentTwoWrapper() // not synchronized
            //doAsyncWorkWithSuspendCancellableCoroutine() // not synchronized ("time taken is :" before only)
        }
        println("time taken is : $time1")
    }
}

suspend fun doBackgroundWork() {
     doAsyncWork()
}

/**
 * if we create new thread for normal hierarchi i.e. without suspendCancellableCoroutine Method
 * then it will run asynchronously but what we want that we need synchronized call hierarchi over bg task
 */
fun doAsyncWork() {
    Thread.sleep(1000)
    println("doAsyncWork end")
}

fun doAsyncWorkIndependent() {
    thread {
        Thread.sleep(1000)
        println("doAsyncWorkIndependent end")
    }
}

fun doAsyncWorkIndependentTwoWrapper() {
    doAsyncWorkIndependentTwo {
        println("doAsyncWorkIndependentTwoWrapper end")
    }
}

fun doAsyncWorkIndependentTwo(block: (String) -> Unit) {
    thread {
        Thread.sleep(1000)
        println("doAsyncWorkIndependentTwo end")
        block("")
    }
}

/**
 * *******************************************************************************
 */

/**
 * blocking the other thread not the underlying thread. But still in synchronized way.
 * Behind the scene it's creating a wrapper to handle the callback flow.
 * Although it will hold the control for caller thread on current execution at suspendCancellableCoroutine
 * but it won't block that thread. That will be free to do other task.
 * We can test it applying the current logic on main thread and check the ANR.
 * So moto here is to hold the control at suspension point to make it synchronized flow and it won't block
 * the underlying thread if we use separate thread. Even separate thread won't block in case of normal flow
 * i.e. without suspension point but that will not look like synchronized.
 */
fun doAsyncWorkIndependentWithSuspendCancellableCoroutine(block: (String) -> Unit) {
    thread {
        Thread.sleep(8000)
        println("doAsyncWorkIndependent end")
        block("result")
    }
}

suspend fun doAsyncWorkWithSuspendCancellableCoroutine() {
    suspendCancellableCoroutine<String> { continuation ->
        doAsyncWorkIndependentWithSuspendCancellableCoroutine { res ->
            continuation.resume(res)
        }
    }
}


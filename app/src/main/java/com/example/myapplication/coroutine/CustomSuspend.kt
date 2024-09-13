package com.example.myapplication.coroutine

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.system.measureTimeMillis

/**
 * https://www.pluralsight.com/courses/kotlin-coroutines-deep-dive
 * https://www.droidcon.com/2024/04/25/how-suspend-functions-work-in-kotlin-under-the-hood/#:~:text=A%20coroutine%20can%20start%20executing,network%20call)%20is%20done%20executing.&text=This%20is%20a%20simple%20program,Before%E2%80%9D%20and%20%E2%80%9CAfter%E2%80%9D.
 * https://chatgpt.com/share/31d35776-83af-4b8f-8d7d-bb99169eac5f
 * Suspending functions are like state machines, with a possible state at the beginning of the function and after each suspending function call.
 * Both the label identifying the state and the local data are kept in the continuation object.
 * Continuation of one function decorates a continuation of its caller function; as a result, all these continuations represent a call stack that
 * is used when we resume or a resumed function completes.
 *
 * Kotlin doesn't automatically convert a suspend function into suspendCancellableCoroutine. Instead, suspendCancellableCoroutine is used when you need
 * to manually bridge non-suspending, callback-based APIs into a suspendable structure.
 * Kotlin automatically handles the suspension at the callNetwork() call without converting it into suspendCancellableCoroutine. You would only
 * use suspendCancellableCoroutine if you were dealing with an asynchronous API that doesn't directly support coroutines or if you needed fine-grained control
 * over cancellation.
 * Automatic handling meant- When you write a suspend function in Kotlin, it doesn't automatically convert the function into
 * a suspendCancellableCoroutine under the hood. Instead, Kotlin compiles suspend functions into state machines that manage the suspension and resumption of
 * the function's execution.
 * In Kotlin, suspended functions are implemented with the Continuation passing style. This means that continuations are passed from function to function
 * as arguments(Same as Composer in Jetpack Compose).
 * suspend fun myFunction() {
 *   println("Before")
 *   delay(1000) // suspending
 *   println("After")
 * }
 * // A simplified picture of how myFunction looks under the hood
 * fun myFunction(continuation: Continuation<Unit>): Any {
 *   if (continuation.label == 0) { //Starting point
 *     println("Before")
 *     continuation.label = 1 //Update just before suspension
 *     if (delay(1000, continuation) == COROUTINE_SUSPENDED){
 *       return COROUTINE_SUSPENDED
 *     }
 *   }
 *   //Point after suspension
 *   if (continuation.label == 1) {
 *     println("After")
 *     return Unit
 *   }
 *   error("Impossible")
 * }
 * The function could be started from two places: either from the beginning (in the case of a first call) or from the point after suspension (in the case
 * of resuming from continuation). To identify the current state, we use a field called label. At the start, it is 0, therefore the function will
 * start from the beginning. However, it is set to the next state before each suspension point so that we start from just after the suspension point after
 * a resume.
 *
 * but suspendCancellableCoroutine - if its not being used internally by coroutine for suspend function. Then why do we need to know about it?
 * That's a great question, and it touches on the distinction between the internal workings of coroutines and the tools available for developers to use
 * in specific scenarios.
 * Bridging Legacy or Third-Party APIs:
 * Callback-Based APIs: Many legacy or third-party libraries provide asynchronous operations using callbacks rather
 * than suspending functions. suspendCancellableCoroutine allows you to wrap these APIs so that you can use them within coroutines in
 * a seamless, idiomatic way.
 * Real-World Use Case: Imagine working with an API that provides network responses via callbacks. To make this API coroutine-friendly, you
 * would wrap the callback in a suspendCancellableCoroutine, transforming it into a suspend function that can be called within coroutines.
 * Manual Control Over Suspension and Resumption:
 * Understanding Coroutine Internals:
 * Handling Edge Cases:
 *
 * suspendCancellableCoroutine is wrapper provided by kotlin coroutine to utilize it by developer where ever possible like where we dont have control to
 * change existing implementation into kotlin inbuilt suspend function. There we can utilize suspendCancellableCoroutine function to call those
 * asynchronous function. Internally it uses CPS style with managing continuation state object with label. Here using suspendCancellableCoroutine, we don't need
 * to be worry abt complexicity of CPS flow.
 *
 */
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
 * i.e. without suspension point(without suspendCancellableCoroutine) but that will not look like synchronized.
 */
private fun doAsyncWorkIndependentWithSuspendCancellableCoroutine(block: (String) -> Unit) {
    thread {
        Thread.sleep(8000)
        println("#custom_suspend, doAsyncWorkIndependent end")
        block("result")
    }
}

suspend fun doAsyncWorkWithSuspendCancellableCoroutine() {
    suspendCancellableCoroutine<String> { continuation ->
        doAsyncWorkIndependentWithSuspendCancellableCoroutine { res ->
            continuation.resume(res)
        }
        println("#custom_suspend, get called before doAsyncWorkIndependentWithSuspendCancellableCoroutine finished but does not matter as suspendCancellableCoroutine will " +
                "be complete only when continuation.resume(res) will get called.")
    }
}


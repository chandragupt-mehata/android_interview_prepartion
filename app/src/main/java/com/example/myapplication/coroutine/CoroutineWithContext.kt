package com.example.myapplication.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

fun main21() {
    runBlocking {
        launch {
            repeat(3) {
                println("inside testWithContext launch")
                delay(100)
            }
        }
        withContext(Dispatchers.IO) {
            repeat(3) {
                println("inside testWithContext withContext entered")
                //sleep(2000)
                delay(100)
            }
            println("inside testWithContext withContext exit")
        }
        println("end")
    }
}

/**
 * runBlocking blocks the current thread until it get completes
 * withContext only suspend it. It switches the thread without blocking the underlying thread.
 */
suspend fun main() {
    val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    CoroutineScope(dispatcher).launch {
        launch {
            repeat(10) {
                delay(500)
                println("inside launch: $it")
            }
        }
        delay(2500)
        println("after runBlocking: ")
        runBlocking {
            repeat(10) {
                delay(1000)
                println("inside runBlocking: $it")
            }
        }
        /*withContext(Dispatchers.IO) {
            repeat(10) {
                delay(1000)
                println("inside withContext: $it")
            }
        }*/
    }.join()
}

class CustomScope(override val coroutineContext: CoroutineContext = Dispatchers.IO + Job()) : CoroutineScope {

}
package com.example.myapplication.coroutine

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * https://write.agrevolution.in/kotlin-coroutines-part-4-cancellation-811cd5a9813a
 */
fun main() {
    runBlocking {
        val parentJob = Job()
        val parentScope = CoroutineScope(parentJob)
        val job = parentScope.launch {
            launch {
                launch {
                    launch(Dispatchers.IO) {
                        executeBenchmark()
                    }
                    try {
                        repeat(5) {
                            delay(500)
                            println("inside sub child coroutine: $it")
                        }
                    } catch (e: CancellationException) {
                        println("inside sub child cancelled")
                    }
                    println("inside sub child coroutine, after finish")
                }
                repeat(5) {
                    delay(500)
                    println("inside child coroutine: $it")
                }
                println("inside child coroutine, after finish")
            }
            delay(1000)
            parentScope.cancel()
        }
        job.join()
        println("end")
    }
}

fun main2c() {
    runBlocking {
        val parentJob = Job()
        val parentScope = CoroutineScope(parentJob)
        val job = parentScope.launch {
            launch {
                val jobv = launch {
                    try {
                        repeat(5) {
                            println("inside child 1 init")
                            delay(100)
                            println("inside child 1")
                        }
                    } catch (e: CancellationException) {
                        println("inside child 1 exception caught")
                    }
                    println("child 1 finished")
                }
                repeat(5) {
                    delay(100)
                    println("inside analysis")
                }
                jobv.invokeOnCompletion {
                    println("it is: $it")
                }
            }
            launch {
                try {
                    delay(200)
                    parentScope.cancel()
                } catch (e: CancellationException) {
                    println("inside child 2 exception caught")
                }
            }
        }
        job.join()
    }
}

private suspend fun executeBenchmark(): Long {
    val benchmarkDurationSeconds = 5
    val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L
    var iterationsCount: Long = 0
    while (System.nanoTime() < stopTimeNano) {
        iterationsCount++
    }
    println("executeBenchmark completed")
    return iterationsCount
}
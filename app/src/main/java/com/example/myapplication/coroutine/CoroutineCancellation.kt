package com.example.myapplication.coroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * https://write.agrevolution.in/kotlin-coroutines-part-4-cancellation-811cd5a9813a
 * A CoroutineScope defines the context in which coroutines run.
 * It provides a structured way to manage the lifecycle of coroutines, especially when launching multiple coroutines that need to be coordinated or
 * cancelled together.
 * Coroutines launched within a scope are cancelled when the scope is cancelled.
 */

class CoroutineCancellation {
}

fun main2() {
    runBlocking {

        val scopeJob = Job()
        val scope = CoroutineScope(scopeJob + CoroutineName("outer scope") + Dispatchers.IO)
        var job1: Job? = null
        var job2: Job? = null
        job1 = scope.launch(CoroutineName("parent")) {
            this.printCoroutineScopeInfo()
            launch(CoroutineName("child1")) {
                this.printCoroutineScopeInfo()
                launch(CoroutineName("sub1 of child1")) {
                    this.printCoroutineScopeInfo()
                    repeat(5) {
                        delay(1000)
                        println("hello: $isActive")
                        printJobsHierarchy(scopeJob)
                        scope.cancel()
                    }
                }
                launch(CoroutineName("sub2 of child1")) {
                    this.printCoroutineScopeInfo()
                    repeat(5) {
                        delay(1000)
                        println("world: $isActive")
                    }
                }
            }
        }
        job2 = scope.launch(CoroutineName("second child")) {
            this.printCoroutineScopeInfo()
            delay(5000)
            println("inside job2")
            repeat(5) {
                delay(1000)
                println("inside job2 second independent one")
            }
            //printJobsHierarchy(scopeJob)
        }
        job1.join()
        job2.join()
    }
}

fun printJobsHierarchy(job: Job, nestLevel: Int = 0) {
    val indent = "    ".repeat(nestLevel)
    println("$indent- $job")
    for (childJob in job.children) {
        printJobsHierarchy(childJob, nestLevel + 1)
    }
    if (nestLevel == 0) {
        println()
    }
}

fun main3() {
    runBlocking {
        val scope = CoroutineScope(Dispatchers.IO)
        val job = scope.launch {
            executeBenchmark()
            println("after executeBenchmark")
        }
        delay(2000)
        println("delay finished")
        //scope.cancel()
        job.cancel()
        delay(5000)
        scope.launch {
            println("inside second re-launched coroutine")
        }
        delay(2000)
    }
}

fun main() {
    runBlocking {
        launch {
            println("hello: second start")
            repeat(5) {
                println("hello: second $it")
                delay(1000)
            }
            println("hello: second end")
        }
        launch {
            val scope = this
            println("hello: first start")
            launch {
                launch(Dispatchers.IO) {
                    delay(1000)
                    println("after delay cancelling it")
                    scope.cancel()
                }
            }
            executeBenchmark()
            println("hello: first end")
        }
    }
}

private suspend fun executeBenchmark(): Long {
    val benchmarkDurationSeconds = 5
    val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds * 1_000_000_000L
    var iterationsCount: Long = 0
    while (System.nanoTime() < stopTimeNano) {
        iterationsCount++
        //delay(10)
        dummySuspend()
    }
    println("executeBenchmark completed")
    return iterationsCount
}

private suspend fun dummySuspend() {
    //delay(50)
}

private suspend fun executeBenchmarkWithDelay(): Long {
    repeat(5) {
        delay(1000)
        println("inside executeBenchmarkWithDelay: $it")
    }
    return 0
}

fun CoroutineScope.printCoroutineScopeInfo() {
    println()
    println("CoroutineScope: $this")
    println("CoroutineContext: ${this.coroutineContext}")
    println("Job: ${this.coroutineContext[Job]}")
    println()
}
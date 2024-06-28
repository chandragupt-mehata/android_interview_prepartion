package com.example.myapplication.coroutine.synchronization

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val nTask = 2
        val nIteration = 1000000
        val counter = SynchronizedCounter()
        val time = exerciseCounter(counter, nTask, nIteration)

        println("counter value: real: ${counter.count} expected: ${nTask * nIteration} in time taken: $time")
    }
}

suspend fun exerciseCounter(counter: Counter, nTasks: Int, nIteration: Int): Long {
    val time = measureTimeMillis {
        val jobs = mutableListOf<Job>()
        repeat(nTasks) {
            jobs.add(GlobalScope.launch {
                repeat(nIteration) {
                    counter.increment()
                }
            })
        }
        jobs.forEach {
            it.join()
        }
    }
    return time
}

suspend fun exerciseCounterWithNormalSingleThreadWithoutSwitching(counter: Counter, nTasks: Int, nIteration: Int): Long {
    val dispatcher = newSingleThreadContext("new single one")

    val time = measureTimeMillis {
        val jobs = mutableListOf<Job>()
        repeat(nTasks) {
            jobs.add(GlobalScope.launch(dispatcher) {
                repeat(nIteration) {
                    counter.increment()
                }
            })
        }
        jobs.forEach {
            it.join()
        }
    }
    return time
}

/*
fun main() {
    runBlocking {
        val nTask = 2
        val nIteration = 5
        val counter = Counter()
        exerciseCounter(counter, nTask, nIteration)

        println("counter value: real: ${counter.count} expected: ${nTask * nIteration}")
    }
}

suspend fun exerciseCounter(counter: Counter, nTasks: Int, nIteration: Int) {
    val jobs = mutableListOf<Job>()
    repeat(nTasks) {
        jobs.add(GlobalScope.launch {
            println("value outside")
            repeat(nIteration) {
                println("value inside on")
                counter.increment()
                println("value inside off")
            }
        })
    }
    jobs.forEach {
        it.join()
    }
}*/

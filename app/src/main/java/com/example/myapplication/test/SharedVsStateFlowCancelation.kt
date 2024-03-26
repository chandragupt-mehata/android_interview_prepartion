package com.example.myapplication.test

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main23() = runBlocking {
    val sharedFlow = MutableSharedFlow<Int>()
    launch {
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        list.forEach {
            delay(1000)
            println("emitValuesCancellation $it")
            sharedFlow.emit(it)
        }
    }

    val job1 = launch {
        sharedFlow.collect {
            println("Collector 1: $it")
        }
    }

    val job2 = launch {
        sharedFlow.collect {
            println("Collector 2: $it")
        }
    }

    delay(2000) // Let it run for a while

    job1.cancel() // Cancel the first collector

    delay(2000) // Let the remaining collector continue

    job2.cancel() // Cancel the second collector

    delay(1000) // Just for observing
}

var counter1 = 0

fun main() {
    runBlocking {
        val stateflow = emitValuesCancellationNew().shareIn(this, SharingStarted.Eagerly)
        delay(2100)
        launch {
            stateflow.collect() {
                println("emitValuesCancellation receiver: $it")
            }
        }
    }
}

fun emitValuesCancellationNew(): Flow<Int> {
    return flow {
        while (true) {
            delay(1000)
            println("emitValuesCancellation $counter1")
            emit(counter1++)
        }
    }
}

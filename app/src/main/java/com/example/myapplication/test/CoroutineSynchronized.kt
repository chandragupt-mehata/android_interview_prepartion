package com.example.myapplication.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    runBlocking {
        val job1 = withContext(Dispatchers.IO) {
            repeat(5) {
                delay(300)
                println("job1")
            }
        }
        val job2 = launch {
            repeat(5) {
                delay(300)
                println("job2")
            }
        }
    }

    val thread1 = Thread {
        testSynchronization()
    }
    val thread2 = Thread {
        Thread.sleep(3000)
        resetCounter()
    }
    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()
}

var testCounter: Int = 0

@Synchronized
fun testSynchronization() {
    repeat(5) {
        Thread.sleep(1000)
        testCounter ++
        println("test counter is: $testCounter")
    }
}

fun resetCounter() {
    testCounter = 0
}



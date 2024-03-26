package com.example.myapplication.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

//don't see thread related diff which is mentioned in chat gpt

/**
 * In summary, while you can use ReentrantLock().lock() in a coroutine context, mutex.withLock is preferred for its coroutine-specific features
 * and more idiomatic syntax. It helps in writing cleaner and safer coroutine-based code in Kotlin. If you are dealing exclusively with
 * traditional multi-threading without coroutines, then using ReentrantLock directly is appropriate. The choice depends on the concurrency model you
 * are working with and the specific features you need.
 *
 * Above definition looks true for mutex vs synchronized and reintrant lock vs synchronized. But both synchronized and reintrant looks working as
 * same with suspended function they allow other coroutine to perform task if locked coroutine will go into suspended state.
 * Its fine here since all coroutine are drawn from single thread and they would be increasing counter one at a time. But main thing is they are not
 * blocking underlying thread. One can test it with multiple coroutine drawn by multiple thread where with mutex it's possible that multiple thread's
 * coroutine can increase the counter at same time which will not give expected result. But with mutex that will give expected result. And
 * good thing is that if other thread's coroutine will try to access that it wont allow until that will go into suspended state.
 */
fun main() {
    /*runBlocking {
        launch {
            processSharedVariableWithMutex()
        }
        launch {
            delay(100)// just to make sure that above launch block will call lock function
            repeat(5) {
                sharedCounter++
                println("hello current thread: $it and name: ${Thread.currentThread().name}")
            }
        }

        launch {
            processSharedVariableWithReintrant()
        }
        launch {
            delay(100)// just to make sure that above launch block will call lock function
            repeat(5) {
                println("hello current thread: $it and name: ${Thread.currentThread().name}")
            }
        }

        delay(500)
        println("sharedCounter: $sharedCounter")
    }*/
    runBlocking {
        verifyUsingMultipleThread()
    }
}

val mutex = Mutex()
val lock = ReentrantLock()
var sharedCounter = 0

suspend fun processSharedVariableWithoutLock() {
    sharedCounter ++
    delay(1000)
}

val any = Any()

suspend fun processSharedVariableWithMutex() {
    mutex.withLock {
        println("current thread inside processSharedVariableWithMutex: locked ${Thread.currentThread().name}")
        sharedCounter ++
        delay(2000)
        //Thread.sleep(2000)
        println("current thread inside processSharedVariableWithMutex: released ${Thread.currentThread().name}")
    }
    /*synchronized(any) {
        println("current thread inside processSharedVariableWithMutex: locked ${Thread.currentThread().name}")
        sharedCounter ++
        //delay(2000)
        Thread.sleep(2000)
        println("current thread inside processSharedVariableWithMutex: released ${Thread.currentThread().name}")
    }*/
}

suspend fun processSharedVariableWithReintrant() {
    lock.lock()
    sharedCounter ++
    delay(5000)
    lock.unlock()
}

suspend fun processSharedVariableWithSynchronized() {
    synchronized(any) {
        sharedCounter ++
        Thread.sleep(4000)
    }
}

@OptIn(ExperimentalTime::class)
suspend fun verifyUsingMultipleThread() {
    val executers = Executors.newFixedThreadPool(1)
    val dispatchers = executers.asCoroutineDispatcher()
    val timeInMs = measureTime {
        CoroutineScope(dispatchers).launch {
            val coroutines = 1.rangeTo(2).map {
                launch(dispatchers) {
                    launch {
                        repeat(20) {
                            delay(100)
                            println("current thread inside repeat: $it and ${Thread.currentThread().name}")
                        }
                    }
                    processSharedVariableWithMutex()
                }
            }
            coroutines.forEach {
                it.join()
            }
        }.join()
    }
    println("shared counter value is: $sharedCounter and time taken is: ${timeInMs.inWholeSeconds}")
}
package com.example.myapplication.coroutine.synchronization

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

val semaphore = Semaphore(1)
val mutex1 = Mutex()

@Synchronized
fun criticalSectionWithSynchronized() {
    try {
        println("Entered thread: ${Thread.currentThread().name}")
        println("inside critical section")
        Thread.sleep(1000)
        println("out of critical section")
    } finally {
    }
}

suspend fun criticalSectionWithSemaphare() {
    try {
        println("Entered thread: ${Thread.currentThread().name}")
        semaphore.acquire()
        println("inside critical section")
        //delay(1000) // Simulating a long operation
        Thread.sleep(10000)
        println("out of critical section")
    } finally {
        semaphore.release()
    }
}

/**
 * using thread sleep will block complete thread, which meant performParallelTaskWithLockedTask correspondent to that thread will be blocked until
 * sleep time is over. One can check in logs.
 * Which meant mutex supports coroutine properties and if we are having some blocking task which internally support suspend mechanism then it's
 * good to use mutex for synchronization rather than using java synchronization property as mutex will not block underlying thread.
 */
suspend fun criticalSectionWithMutex() {
    mutex1.withLock {
        println("Entered thread: ${Thread.currentThread().name}")
        println("inside critical section")
        delay(5000) // Simulating a long operation
        //Thread.sleep(5000)
        println("out of critical section ${Thread.currentThread().name}")
    }
}

/**
 * confusion: although I am sleeping one thread but still performParallelTaskWithLockedTask is being executed correspondent to that thread.
 * That was there because we are calling getDispatcher each time inside loop which will create a new instance for dispatcher1 and dispatcher2 as
 * there are defined inside function.
 * Solution: Better to declare them as top level variables.
 */
fun main() {
    val dispatcher: CoroutineContext = newSingleThreadContext("single")
    val executor = Executors.newFixedThreadPool(2)

    runBlocking {
        repeat(2) {sequenceLoopNo ->
            launch(getDispatcher(sequenceLoopNo)) {
                delay(100)
                println("coroutine launched: inside : $sequenceLoopNo Thread name: ${Thread.currentThread().name}")
                launch(getDispatcher(sequenceLoopNo)) {
                    repeat(5) {
                        println("performParallelTaskWithLockedTask: inside: $sequenceLoopNo  and thread: ${Thread.currentThread().name}")
                        delay(1000)
                    }
                }
                criticalSectionWithMutex()
            }
        }
    }
    executor.shutdown()
}

val dispatcher1: CoroutineContext = newSingleThreadContext("one")
val dispatcher2: CoroutineContext = newSingleThreadContext("two")

fun getDispatcher(sequenceLoopNo: Int): CoroutineContext {
    //return if (sequenceLoopNo == 1) newSingleThreadContext("one") else newSingleThreadContext("two")
    return if (sequenceLoopNo == 1) dispatcher1 else dispatcher2
}

suspend fun performParallelTaskWithLockedTask(coroutineScope: CoroutineScope) {
    coroutineScope.launch(dispatcher1) {
        repeat(5) {
            println("performParallelTaskWithLockedTask: $it and thread: ${Thread.currentThread().name}")
            delay(1000)
            println("performParallelTaskWithLockedTask: end $it and thread: ${Thread.currentThread().name}")
        }
    }
}

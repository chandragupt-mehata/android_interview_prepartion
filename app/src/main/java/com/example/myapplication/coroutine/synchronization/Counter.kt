package com.example.myapplication.coroutine.synchronization

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

open class Counter {

    open var count: Int = 0
        protected set

    open suspend fun increment() {
        count++
    }
}

class SynchronizedCounter: Counter() {
    private var object1 = Any()
    override suspend fun increment() {
        synchronized(object1) {
            count++
        }
    }
}

class MutexCounter: Counter() {
    override suspend fun increment() {
        mutex.withLock {
            super.increment()
        }
    }
}


fun main() {
    testAtomic2()
}

class AtomicCounter: Counter() {

    private val atomicCounter = AtomicInteger()
    override suspend fun increment() {
        atomicCounter.getAndIncrement()
    }

    override var count: Int
        get() = atomicCounter.get()
        set(value) {}
}

class SingleThreadCounter(private val dispatcher: CoroutineContext = newSingleThreadContext("single")): Counter() {
    override suspend fun increment() {
        withContext(dispatcher) {
            super.increment()
        }
    }
}

data class AtomicData(var value: Int) {
    constructor() : this(0) {
        println("inside constructor")
    }
}

fun testAtomic() {
    val atomicReference: AtomicReference<AtomicData> = AtomicReference(AtomicData(0))
    val t1 = thread {
        for (i in 1..100) {
            //atomicReference.get().value++
            updateAtomicValue(atomicReference)
            Thread.sleep(10)
        }
    }
    val t2 = thread {
        for (i in 1..100) {
            //atomicReference.get().value++
            updateAtomicValue(atomicReference)
            Thread.sleep(10)
        }
    }
    t1.join()
    t2.join()
    println("verify synchronization: ${atomicReference.get().value} and expected one is: 200")
}

fun updateAtomicValue(atomicReference: AtomicReference<AtomicData>) {
    while (true) {
        val currentData = atomicReference.get()
        val newData = currentData.copy(value = currentData.value + 1)
        if (atomicReference.compareAndSet(currentData, newData)) {
            break
        }
        // If compareAndSet fails, another thread has updated the value, so try again.
    }
}

fun updateAtomicValueWithGetAndSet(atomicReference: AtomicReference<AtomicData>) {
    /*var updated: AtomicData
    do {
        val currentData = atomicReference.get()
        updated = currentData.copy(value = currentData.value + 1)
    } while (atomicReference.getAndSet(updated) != currentData)*/
}


/**
 * The issue in your code arises from the use of globalCounter in combination with AtomicReference. The getAndSet operation itself
 * is atomic, but the problem lies in how globalCounter is incremented and then passed to AtomicReference.
 *
 * Why It Doesn't Work as Expected
 * Global Counter Increment Is Not Atomic:
 * The increment operation globalCounter++ is not atomic. This means that when multiple threads are incrementing globalCounter simultaneously, race
 * conditions can occur. Each thread might read the same value of globalCounter before any thread has a chance to increment it, leading to
 * lost updates and incorrect counts.
 *
 * Atomicity Only Applies to getAndSet:
 * The getAndSet operation ensures that the reference update is atomic, but it doesn't control the atomicity of the value being passed to it. In this
 * case, globalCounter++ is evaluated separately from getAndSet, and thus, race conditions can occur before the new value is passed to getAndSet.
 *
 */


var globalCounter = 0
val globalCounterAtomic = AtomicInteger(0)

fun testAtomic2() {
    val atomicReference: AtomicReference<AtomicData> = AtomicReference(AtomicData(globalCounter))

    val t1 = thread {
        for (i in 1..100) {
            atomicReference.getAndSet(AtomicData(globalCounter++))
            Thread.sleep(10)
        }
    }
    val t2 = thread {
        for (i in 1..100) {
            atomicReference.getAndSet(AtomicData(globalCounter++))
            Thread.sleep(10)
        }
    }
    t1.join()
    t2.join()
    println("verify synchronization: ${atomicReference.get().value} and expected one is: 200")
}
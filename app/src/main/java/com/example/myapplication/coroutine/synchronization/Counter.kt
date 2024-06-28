package com.example.myapplication.coroutine.synchronization

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
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
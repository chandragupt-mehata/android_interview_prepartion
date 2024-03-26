package com.example.myapplication.test

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        cancelBlockingCoroutineFlow()
        println("end")
    }
}

// But after cancellation - emitter of state flow and shared flow with SharingStarted.Eager/SharingStarted.Lazily will keep on emitting the values
// where as emitter of SharingStarted.WhileSubscribed will get stopped the moment collector got cancelled. But of course main is not being finished

// Noted here: If we want to see the cancellation behaviour then it's necessary to put sharedFlow object like sharedFlow1 outside of the cancellation
// scope if we put inside cancellation scope then definetly flow will be cancelled after canclling the scope no matter what kind of shared flow it is
suspend fun cancelBlockingCoroutineFlow() {
    runBlocking {
        val sharedFlow1 = emitValuesCancellation().shareIn(this, SharingStarted.WhileSubscribed())
        coroutineScope {
            launch {
                sharedFlow1.collect {
                    println("collecting value: in one $it")
                    if (it > 3) {
                        this.cancel()
                    }
                }
                println("end")
            }
            /*delay(4000)
            launch {
                sharedFlow1.collect {
                    println("collecting value: in two $it")
                    if (it > 5) {
                        this.cancel()
                    }
                }
                println("end")
            }*/
        }
    }
}

suspend fun analyzeSharedFlow() {
    runBlocking {
        val sharedFlow1 = emitValues().shareIn(this, SharingStarted.WhileSubscribed())
        // to verify shared flow use single object value i.e. sharedFlow1 if you will use diff object like sharedFlow1 and sharedFlow2
        // then it will be treated as new flow always
        // don't watch cheezy code they are always using diff object
        val sharedFlow2 = emitValues().shareIn(this, SharingStarted.WhileSubscribed())
        println("sharedFlow1 $sharedFlow1 and sharedFlow2: $sharedFlow2")
        coroutineScope {
            launch {
                sharedFlow1.collectLatest {
                    println("collecting value: in one $it")
                }
                println("end")
            }
            delay(4000)
            launch {
                sharedFlow1.collect {
                    println("collecting value: in two $it")
                }
                println("end")
            }
        }
    }
}

suspend fun analyzeStateFlow() {
    coroutineScope {
        val stateValues = emitStateValues()
        launch {
            emitStateValues().collect {
                println("collecting value: in one $it")
            }
            println("end")
        }
        delay(4000)
        launch {
            emitStateValues().collect {
                println("collecting value: in two $it")
            }
            println("end")
        }
    }
}

fun emitValues(): Flow<Int> {
    return flow {
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        list.forEach {
            delay(1000)
            emit(it)
        }
    }
}

fun emitValuesCancellation(): Flow<Int> {
    return flow {
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        list.forEach {
            delay(1000)
            println("emitValuesCancellation $it")
            emit(it)
        }
    }
}

suspend fun emitStateValues(): Flow<Int> {
    val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val stateFlow = MutableStateFlow(0)
    GlobalScope.launch {
        list.forEach {
            delay(1000)
            stateFlow.emit(it)
        }
    }
    return stateFlow
}


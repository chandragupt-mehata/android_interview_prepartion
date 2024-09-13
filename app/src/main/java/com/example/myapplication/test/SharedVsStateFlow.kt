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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * StateFlow vs LiveData:
 * LiveData is lifecycle aware. That means it will not easy for us to use anywhere in our project. On other hand, StateFlow comes with a lot of extra functionality and we can also use it anywhere.
 * Performance is impacted in LiveData, not in all cases but still "sometimes" makes the difference. How? In LiveData, if we want to map, filter, modify our output then we will do that on our main thread. But in StateFlow, we can specify our thread by using flowOn().
 * Flows provide us with more functionality and operators.
 * At this point, you have a good understanding of Kotlin Flow, SharedFlow, StateFlow, LiveData. Now, let me briefly tell you the cases for where to use which one:
 *
 * If you want to execute independent/new stream of data for every collector and also only emit when there is a collector then use Flow.
 * If you want to share your data stream and emit it anytime irrespective of any collector collecting or not, then use SharedFlow.
 * If you want to save the last state of our data stream and want to use it anywhere(not only for lifecycle aware components) then use StateFlow.
 */

// https://chatgpt.com/share/218d22ea-cbca-499d-8e3d-dbfe0b0da8af
fun main() {
    runBlocking {
        analyzeStateFlow()
        println("end")
    }
}

// As both state and shared flow are hot flows they will never complete by themselves.
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
        val stateValues = emitStateValues().stateIn(this)
        // if we remove state in and treat it as normal flow then it will behave like cold flow
        // i.e. each subscriber will get new emission of flow
        // https://outcomeschool.com/blog/cold-flow-vs-hot-flow
        launch {
            stateValues.collect {
                println("collecting value: in one $it")
            }
            println("end")
        }
        delay(4000)
        launch {
            stateValues.collect {
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


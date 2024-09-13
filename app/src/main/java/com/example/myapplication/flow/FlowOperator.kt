package com.example.myapplication.flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        /*getflow().collectLatest {
            println("value in collector first: $it")
            delay(1000)
            println("value in collector second: $it")
        }*/

        /*getflow().take(5).conflate().collect() {
            println("value in collector first: $it")
            delay(1000)
            println("value in collector second: $it")
        }
        println("end of flow") // when we will receive fifth item, emitter along with collector will get stopped
        delay(10000)*/

        // if different thread for emitter then it wont wait for collector to process the emitted values
        /*getflow().flowOn(Dispatchers.IO).collect() {
            delay(1000)
            println("value in collector: $it, and thread name is ${Thread.currentThread().name}")
        }*/

        // if single thread for emitter and collector then it will wait
        /*val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        launch(dispatcher) {
            getflow().flowOn(dispatcher).collect() {
                delay(1000)
                println("value in collector: $it, and thread name is ${Thread.currentThread().name}")
            }
        }*/

        /*val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        launch(dispatcher) {
            getflow().flowOn(dispatcher).reduce {
                    a, b -> a+b
            }.collect() {
                delay(1000)
                println("value in collector: $it, and thread name is ${Thread.currentThread().name}")
            }
        }*/

        //reduce and fold operator
        /*val result = getflow().take(5).toList() // terminal operator
        println("result is : $result")

        val result1 = getflow().take(5).reduce { a, b -> // terminal operator
            a + b
        }

        val result2 = getflow().take(5).fold(0) { a, b -> // terminal operator
            a + b
        }

        println("resul1: $result1 and result2: $result2")*/

        //collectLatest: cancel and restart slow collector and its terminal operator
        /*val collect = getflow().take(5).collectLatest {
            println("came inside")
            delay(400)
            println("processed value: $it")
        }*/

        //conflate: non terminal operator. Once it will process the collected value then at that time what ever value will get emitted will come inside,
        // prior to that all other emitted value will not come inside, those will get discarded.
        // in collect latest each value will come inside but it will get canceled and restarted if before processing next item will get emitted
        /*val collect1 = getflow().take(15).conflate().collect {
            println("came inside")
            delay(400)
            println("processed value: $it")
        }*/

        runBlocking {
            analyzeSharedFlow2()
        }
    }
}

suspend fun analyzeSharedFlow2() {
    runBlocking {
        val sharedFlow1 = emitValues().stateIn(this)
        // to verify shared flow use single object value i.e. sharedFlow1 if you will use diff object like sharedFlow1 and sharedFlow2
        // then it will be treated as new flow always
        // don't watch cheezy code they are always using diff object
        val sharedFlow2 = emitValues().stateIn(this)
        delay(3000)
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

fun getflow(): Flow<Int> {
    var counter = 0
    return flow<Int> {
        while (true) {
            delay(100)
            println("value in emitter: $counter, and thread name is ${Thread.currentThread().name}")
            emit(counter++)
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
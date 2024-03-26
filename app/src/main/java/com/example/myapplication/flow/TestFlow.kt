package com.example.myapplication.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
case 0: directly calling emitDataLocal().collect() will create a new instance always it meant for each collector it would be new
instance and they will behave as per their individual subscription

case 1: when it's only flow it meant it will only start emiting data once it will have subscriber i.e. collecter
note: val data = emitDataLocal() it will make sure that always all collector will have single object otherwise
directly calling emitDataLocal().collect() will create a new instance always

case 2: when we convert flow to shared flow then it will behave like hot flow that meant your flow will keep only pushing
data based on SharingStarted and all collecter will have same value when ever they will come into picture based on delay
 */

fun main() {
    runBlocking {
        val data = emitDataLocal() // case 1
        //val data = emitDataLocal().shareIn(this, SharingStarted.WhileSubscribed()) // case 2
        //val data = emitDataLocal().shareIn(this, SharingStarted.Lazily) // case 3
        //val data = emitDataLocal().shareIn(this, SharingStarted.Eagerly) // case 4

        delay(2000)
        launch {
            //val data = emitDataLocal().shareIn(this, SharingStarted.Eagerly) //case 0
            data.collect() {
                println("collecter 1: $it, : $data")
            }
        }
        delay(5000)
        launch {
            //val data = emitDataLocal().shareIn(this, SharingStarted.Eagerly) //case 0
            data.collect() {
                println("collecter 2: $it, : $data")
            }
        }
    }
}

private fun emitDataLocal(): Flow<Int> {
    return flow {
        for (i in 1..10000) {
            delay(1000)
            emit(i)
        }
    }
}

fun testIt() {
    for (item in 1..15) {
        break
    }
}
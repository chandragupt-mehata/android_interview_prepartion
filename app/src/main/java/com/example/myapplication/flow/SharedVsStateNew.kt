package com.example.myapplication.flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() {
    runBlocking {
       launch {
           supervisorScope {
               launch {
                   repeat(5) {
                       delay(100)
                       println("one")
                   }
               }
               launch {
                   repeat(5) {
                       delay(100)
                       println("two")
                   }
               }
           }
           launch {
               println("independent one")
           }
       }
    }
}

fun emitFlow() = flow<Int> {
    repeat(5) {
        emit(it)
        delay(100)
        println("emitting: $it")
    }
}
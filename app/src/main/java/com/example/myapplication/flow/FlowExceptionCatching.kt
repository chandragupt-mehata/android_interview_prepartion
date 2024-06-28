package com.example.myapplication.flow

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.Exception

fun main() {
    runBlocking {
        try {
            emitDataWithoutCatch().collect {
                check(it < 2) {
                    "throw from collector"
                }
                println("value inside collector is: $it")
            }
        } catch (e: Exception) {
            println("exception caught inside collector is: ${e.message}")
        }
    }
}

/**
 * If we catch exception inside emitter regardless of whether we throw exception from emitter or collector, it will
 * always get caught inside emitter catch block. And more importantly it won't cancel flow.
 */
fun emitData() = flow<Int> {
    repeat(10) {
        try {
            check(it < 5) {
                "throw from emitter"
            }
            emit(it)
        } catch (e: Exception) {
            println("catching exception in emitter")
        }
    }
}

/**
 * if we will not catch inside emitter and we catch inside collector, it will cancel the flow once it encounters first exception.
 */
fun emitDataWithoutCatch() = flow<Int> {
    repeat(10) {
        check(it < 5) {
            "throw from emitter"
        }
        emit(it)
    }
}

fun <T> wrapper() = flow<T> {

}
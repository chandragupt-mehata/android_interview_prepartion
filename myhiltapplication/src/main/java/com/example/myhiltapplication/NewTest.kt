package com.example.myhiltapplication

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

suspend fun main() {
    coroutineScope {
        task1()
        task2()
    }
}

suspend fun task1() {
    while (true) {
        println("hello inside first coroutine")
    }
}

suspend fun task2() {
    for (item in 1..10) {
        delay(100)
        println("hello inside second coroutine")
    }
}
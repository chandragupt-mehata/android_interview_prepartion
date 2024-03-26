package com.example.myapplication.test

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch {
            while (true) {
                 println("inside 1")
            }
        }
        launch {
            while (true) {
                println("inside 2")
            }
        }
    }
}
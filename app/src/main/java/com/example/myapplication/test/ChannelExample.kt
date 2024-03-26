package com.example.myapplication.test

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Example {
    val expensiveProperty: String by lazy {
        println("Computing the value...")
        "This is the result"
    }
}

fun main(args: Array<String>) {
    val example = Example()
    println(example.expensiveProperty)
    println(example.expensiveProperty)
}

fun main2() {
    val str: String? = null
    val obj: Any = "The variable obj is automatically cast to a String in this scope"

    runBlocking {
        launch {
            repeat(5) {
                channel.send(it)
                println("emitting value: $it")
            }
            channel.close()
        }
        launch {
            //val item = channel.receive()
            //println("received value: $item")
            for (item2 in channel) {
                println("received value: inside loop $item2")
            }
            /*channel.consumeEach {
                println("received value: inside loop $it")
            }*/
        }
    }
}

val channel = Channel<Int>()
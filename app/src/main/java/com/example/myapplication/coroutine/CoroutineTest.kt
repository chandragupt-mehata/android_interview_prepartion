package com.example.myapplication.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() {
    val scores = arrayOf("45", null)
    val z = listOfNotNull(scores)
    println("${scores === z}")
    val str = "test"
    (str as? ImplementedClass)?.value
    println("prefix".extensionFun("cool"))
    runBlocking {
        runBlocking {
            val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                println("caught exception: $throwable")
            }
            val rootScope = CoroutineScope(Job() + exceptionHandler)
            supervisorScope {
                val job = launch {
                    repeat(3) {
                        delay(1000)
                        println("inside 1: $it")
                    }
                }
                launch {
                    repeat(10) {
                        delay(1000)
                        println("inside 2: $it and isActive: $isActive")
                    }
                }
                delay(2000)
                cancel()
            }
        }
        println("end")
    }
}

fun String.extensionFun(str: String): String {
    return str.substring(0, 2).plus(this)
}

fun testNow() {
    val x = 10
    when {
        x > 10 -> {
            println()
        }
        x ==19 -> {

        }
        x < 45 -> {

        }
    }
    when (x) {

    }
    null ?: ""
    println("inside test now")
    println("inside test now two")
}

interface CommonInterface {

}

open class ImplementedClass(val value: String): CommonInterface {

}

open class PrimaryConstructor {
    constructor(str: String) {

    }
}

class ExtendingPrimaryConstructor: PrimaryConstructor {
    constructor(): super("")

}
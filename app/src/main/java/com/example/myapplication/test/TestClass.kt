package com.example.myapplication.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.LinkedList
import java.util.concurrent.Executors

fun main() {
    val rectangle: Rectangle = Square()
    rectangle.inside()

    val linkedList = LinkedList<String>()
    //linkedList.get()
    higherOrderFunction { abc: String, def: String ->
        abc + def
    }

    val lambda = {
        x: String, y: String ->
        ""
    }
    higherOrderFunction(lambda)
    higherOrderFunction(::input)

    //

    //chatGptExample()
    runBlocking {
        launch {
            println("inside launch")
            val result = coroutineScope {
                launch {
                    repeat(5) {
                        delay(100)
                        println("inside first: $it")
                    }
                }
                launch {
                    repeat(5) {
                        delay(100)
                        println("inside second: $it")
                    }
                }
                "cool"
            }
            println("end of coroutine scope: $result")
        }
    }
    println("end of runBlocking")
}
val mutex = Mutex()

fun flowVsState() {
    runBlocking {
        var stateObject by mutableStateOf(0)
        val stateFlowObject = MutableStateFlow(0)
        val executers = Executors.newFixedThreadPool(20)
        launch(executers.asCoroutineDispatcher()) {
            val jobs = mutableListOf<Job>()
            repeat(1000) {
                val job = launch(executers.asCoroutineDispatcher()) {
                    println("above inside mutex: $it")
                    delay(10)
                    stateFlowObject.value = stateFlowObject.value + 1
                }
                jobs.add(job)
            }
            for (job in jobs) {
                job.join()
            }
        }.join()
        println("stateObject: $stateObject and stateFlowObject: ${stateFlowObject.value}")
    }
    println("end of flowVsState")
}

fun chatGptExample() {
    runBlocking {
        val stateFlowObject = MutableStateFlow(0)

        // Launch multiple coroutines to concurrently modify the stateFlowObject
        val jobs = List(10) {
            GlobalScope.launch {
                repeat(100) { // Modify 100 times
                    stateFlowObject.value++
                }
            }
        }

        // Wait for all coroutines to complete
        jobs.forEach { it.join() }

        // Print the final value
        println("Final value: ${stateFlowObject.value}") // Output should always be 1000
    }

}


fun input(str: String, str1: String): String {
    return ""
}

fun higherOrderFunction(lambda: (String, String) -> String) {
    lambda("", "")
    /*MyParent.NestedClass().accessIt()
    MyParent().InnerClass().accessParentproperty()*/
}

class MyParent {

    val abc: String = ""

    class NestedClass {
        fun accessIt() {
            //abc
        }
    }

    inner class InnerClass {
        fun accessParentProperty() {
            abc
        }
    }

    fun testMethod() {
        /*val abc = mutableStateOf(Parent())
        abc.co*/
    }

}

interface Consumer<in T> {
    fun consume(item: T)
}

interface Producer<out T> {
    fun produce(): T
}

class NormalGeneric(val value: String): Consumer<String>, Producer<String> {
    override fun consume(item: String) {
        val any: Any = item
    }

    override fun produce(): String {
        return value
    }

    fun testIt() {
        val producer: Producer<Any> = NormalGeneric("Hello, Kotlin!")
        val generic: GenericOut<Parent> = GenericOut(Child())


        val parent: GenericIn<Child> = GenericIn<Parent>()
    }

}

class GenericOut<out T>(val t: T) {
    fun getValues(): T {
        return t
    }
}

class GenericIn<in T>() {
    fun doNot() {

    }
}

//Liskov
open class Rectangle {
    open fun inside() {
        println("inside rectangle")
    }
}

class Square: Rectangle() {
    override fun inside() {
        println("inside square")
    }
}




package com.example.myapplication.test

import android.annotation.SuppressLint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@SuppressLint("SuspiciousIndentation")
fun main() {
    val listString = ArrayList<String>()
    listString.add("")
    listString.add("")

    val xyz: List<Any> = listString
    runBlocking {
        /*val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            launch {

            }
            //withContext(Dispatchers.IO) {
            var count = 0
            while (count < 5) {
                Thread.sleep(1000)
                //ensureActive()
                println("I am running: $count")
                count++
            }
            //}
            println("end of withContext")
        }
        delay(1000)
        scope.cancel()
        println("cancellation done")
        delay(5000)*/

        runBlocking {
            getFlowLocal().map {
                delay(1000)
                println("inside map: thread: ${Thread.currentThread().name}")
                it * 2
            }.flowOn(Dispatchers.IO).collect {
                delay(2000)
                println("inside collector: $it and thread: ${Thread.currentThread().name}")
            }

            val result = getFlowLocal().reduce() { a, b ->
                a + b
            }
            println("result is: $result")
        }
    }
}

open class PrivateConstructor internal constructor(val abc: String)

class ChildClass : PrivateConstructor("") {
    val str: String = ""
}

fun getFlowLocal() = flow<Int> {
    (1..15).forEach {
        delay(100)
        emit(it)
        println("emitting : $it and thread: ${Thread.currentThread().name}")
    }
}


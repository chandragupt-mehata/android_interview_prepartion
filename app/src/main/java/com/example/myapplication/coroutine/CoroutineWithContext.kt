package com.example.myapplication.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

fun main() {
    runBlocking {
        launch {
            repeat(3) {
                println("inside testWithContext launch")
                delay(100)
            }
        }
        withContext(Dispatchers.IO) {
            repeat(3) {
                println("inside testWithContext withContext entered")
                //sleep(2000)
                delay(100)
            }
            println("inside testWithContext withContext exit")
        }
        println("end")
    }
}

class CustomScope(override val coroutineContext: CoroutineContext = Dispatchers.IO + Job()) : CoroutineScope {

}
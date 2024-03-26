package com.example.myapplication.coroutineexceptionhandler

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.RuntimeException

class CoroutineExceptionHandlerDemoTest {

    @Test
    fun uncaughtException() {
        runBlocking {
            val scope = CoroutineScope(Dispatchers.Default)
            val job = scope.launch {
                delay(50)
                throw RuntimeException()
            }
            job.join()
        }
        Thread.sleep(100)
        println("test completed")
    }

    @Test
    fun caughtException() {
        runBlocking {

            val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
                println("Caught exception: $throwable")
            }

            val scope = CoroutineScope(Dispatchers.Default + coroutineExceptionHandler)
            val job = scope.launch {
                delay(50)
                throw RuntimeException()
            }
            job.join()
        }
        Thread.sleep(100)
        println("test completed")
    }

    @Test
    fun caughtExceptionAtCoroutineLevel() {
        runBlocking {

            val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
                println("Caught exception: $throwable")
            }

            val scope = CoroutineScope(Dispatchers.Default)
            val job = scope.launch(coroutineExceptionHandler) {
                delay(50)
                throw RuntimeException()
            }
            job.join()
        }
        Thread.sleep(100)
        println("test completed")
    }




}
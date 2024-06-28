package com.example.myapplication.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * inbuilt suspend function like delay, withContext regularly check if scope is canceled or not like job is active or not
 * in case of not active, they throw cancellation exception which leads to complete coroutine exceptionally.
 * If we are using our own suspend function then, will have to make sure they support cancellation and accordingly cooperative feature.
 *
 * https://www.udemy.com/course/coroutines-on-android/learn/lecture/22551118#overview
 * NonCancellable is designed for withContext to prevent cancellation of code blocks that need to be executed without cancellation
 */
fun main() {
    // #1 : normal cancellation scene
    runBlocking {
        val rootScope = CoroutineScope(Job())
        rootScope.launch() {
            val job = launch {
                repeat(5) {
                    delay(1000)
                    println("inside 1: $it")
                }
            }
            launch {
                repeat(15) {
                    delay(1000)
                    println("inside 2: $it and isActive: $isActive")
                }
            }
            delay(2000)
            job.cancel()
        }.join()
    }
    println("end")

    // #2 : with try catch. If exception will caught (like delay will throw cancellation exception) and no other block will get called of try block
    // but bottom of try block will be called. But again scope of that launch builder will be cancelled so we wont able to launch new coroutine again.
    /*runBlocking {
        val rootScope = CoroutineScope(Job())
        rootScope.launch() {
            val job = launch {
                try {
                    repeat(5) {
                        delay(1000)
                        println("inside 1: $it")
                    }
                } catch (exception: Exception) {
                    println("inside 1: exception: ${exception.message}")
                }
                launch {
                    println("inside 1: second launch builder")
                }
                println("after 1 job finished")
            }
            launch {
                repeat(15) {
                    delay(1000)
                    println("inside 2: $it and isActive: $isActive")
                }
            }
            delay(2000)
            job.cancel()
        }.join()
    }*/

    // #3: Noncancellable coroutine. Made changes. this ex is confusing..
    /*runBlocking {
        val rootScope = CoroutineScope(Job())
        rootScope.launch() {
            val job = launch {
                try {
                    repeat(5) {
                        delay(1000)
                        println("inside 1: $it")
                    }
                } catch (exception: Exception) {
                    println("inside 1: exception: ${exception.message}")
                }
                //delay(100)// if we uncomment it then it will throw Cancellation exception and coroutine completes exceptionaly so below launch block will not get execute
                // even if its with NonCancellable. Although if its not with NonCancellable then also it won't get execute even above delay will be uncommenteded
                // because scope itself is cancelled, we wont be able to launch new coroutine however we can print some log that will get execute.
                launch(NonCancellable) {
                    repeat(5) {
                        delay(1000)
                        println("inside 1 second: $it")
                    }
                }
                println("after 1 job finished")
            }
            launch {
                repeat(15) {
                    delay(1000)
                    println("inside 2: $it and isActive: $isActive")
                }
            }
            delay(2000)
            job.cancel()
        }.join()
    }*/
}


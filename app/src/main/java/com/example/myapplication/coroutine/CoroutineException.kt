package com.example.myapplication.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import java.lang.RuntimeException
import kotlin.Exception

/**
 * https://www.netguru.com/blog/exceptions-in-kotlin-coroutines
 *
 * https://www.youtube.com/watch?v=Pgek3_3vPU8
 * https://www.lukaslechner.com/coroutines-exception-handling-cheat-sheet/
 * https://www.youtube.com/watch?v=w0kfnydnFWI (jetbrains developer)
 * coroutine builder does not re throw exception they propogate to parent
 *
 * ceh should be handled at root coroutine / or parent scope / direct child of supervisor scope or anywhere above that/
 * coroutineScope : (must be handled at root coroutine / or parent scope)
 * ***** root coroutine / or parent scope handling always work
 * above all for launch/ for async need to check
 *
 * **
 * supervisor job should be direct parent of coroutine which throws exception then only it will avoid cancellation
 *
 */
fun main() {
    //caseOne()
    //caseTwo()
    //caseThree()
    //caseFour()
    //caseFive()
    //caseSix()
    //caseSeven()
    //caseEight()
    //caseNine()
    //caseTen()
    caseElevan()
}

/**
 * in case of supervisorScope it will allow other coroutine to run if exception is thrown by any child coroutine which is inside of supervisor scope no
 * matter how much down level that coroutine is. But it wont stop app to be crashed, will have to install ceh.
 * For ceh installing in case of supervisor scope already has been explored in diff example.
 */
fun caseElevan() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("caught exception: $throwable")
    }
    runBlocking {
        val rootScope = CoroutineScope(Job())
        rootScope.launch() {
            launch {
                supervisorScope {
                    launch {
                        launch {
                            throw RuntimeException("throw exception since no ceh but it will allow other coroutine to complete")
                        }
                    }
                }
                repeat(5) {
                    delay(100)
                    println("hi: $it")
                }
            }
        }.join()
    }
}

fun caseTen() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("caught exception: $throwable")
    }
    runBlocking {
        val rootScope = CoroutineScope(Job())
        rootScope.launch() {
            launch(exceptionHandler) {
                coroutineScope {
                    launch() {
                        throw RuntimeException("throw exception since ceh at not parent scope")
                    }
                }
            }
        }.join()
    }
}

fun caseNine() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("caught exception: $throwable")
    }
    runBlocking {
        val rootScope = CoroutineScope(Job())
        rootScope.launch() {
            coroutineScope {
                launch(exceptionHandler) { //installing ceh here will crash because coroutine scope will propagate to further parent who does not
                    //know anything about ceh i.e. exceptionHandler so install it to parent
                    //one more thing coroutine scope throws exception so we can have try catch and catch that exception but we should
                    //directly put coroutineScope with try catch.
                    //Second thing, if we are trying to install ceh then that must be at root coroutine or in parent scope
                    //like caseSeven/caseSix if it won't be at that then it will crash like caseTen
                    throw RuntimeException("exception should be handled at direct child of supervisor scope or anywhere above that")
                }
            }
        }.join()
    }
}

fun caseEight() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("caught exception: $throwable")
    }
    runBlocking {
        val rootScope = CoroutineScope(Job())
        rootScope.launch() {
            supervisorScope {
                launch(exceptionHandler) {
                    throw RuntimeException("exception should be handled at direct child of supervisor scope or anywhere above that")
                }
                launch {
                    while (true) {
                        delay(1000)
                        println("hello")
                    }
                }
            }
        }.join()
    }
}

fun caseSeven() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("caught exception: $throwable")
    }
    runBlocking {
        val rootScope = CoroutineScope(Job())
        rootScope.launch(exceptionHandler) {
            launch {
                launch {
                    throw RuntimeException("exception should be handled at root coroutine")
                }
            }
        }.join()
    }
}

fun caseSix() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("caught exception: $throwable")
    }
    runBlocking {
        val rootScope = CoroutineScope(Job() + exceptionHandler)
        rootScope.launch {
            launch {
                launch {
                    throw RuntimeException("exception should be handled at parent scope")
                }
            }
        }.join()
    }
}

/**
 * coroutine scope will re-throw the exception which we can catch using try catch but it won't stop in cancelling the it's sibling coroutiens
 * that meant coroutine withing same child scope. In below example it's child 1 and child 2
 * But other coroutine which are not sibling of that coroutine will keep on running i.e. child 3
 * If we remove coroutineScope { // scope line no 58 with launch then child 3 will also not run
 */
fun caseFive() {
    runBlocking {
        val rootScope = CoroutineScope(Job())
        rootScope.launch {
            launch { // child 3
                repeat(5) {
                    delay(1000)
                    println("inside child 3: $it")
                }
            }
            try {
                coroutineScope { // scope
                    launch {
                        launch { // child 1
                            repeat(5) {
                                if (it == 2) {
                                    throw RuntimeException("exception")
                                }
                                delay(1000)
                                println("inside child 1: $it")
                            }
                        }
                        launch { // child 2
                            repeat(5) {
                                delay(1000)
                                println("inside child 2: $it")
                            }
                        }
                    }
                }
            } catch (exception: Exception) {
                println("exception: 2 ${exception.message}")
            }
        }.join()
    }
}

fun caseFour() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("caught exception: $throwable")
    }
    runBlocking() {
        val job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            launch {
                launch {
                    delay(500)
                    throw RuntimeException("")
                }
                launch {
                    repeat(5) {
                        delay(1000)
                        println("inside launch 2: $it")
                    }
                }
            }
        }
        job.join()
    }
}

/**
 * even if job1 is throwing exception it will propogate to parent then parent will cancell all child
 * now since job2 which is one of the child of parent is written in try catch then exception will be caught there.
 * But since the scope is cancelled for that child, we cant utilize the scope for other suspend fn but something if written as an custom coroutoune scope i.e.
 * independent one that will run
 */
// for cancellation on exception
fun caseThree() {
    runBlocking {
        val job = CoroutineScope(Dispatchers.IO).launch {
            coroutineScope {
                val job1 = launch {
                    delay(1000)
                    throw RuntimeException("error")
                }
                val job2 = launch {
                    try {
                        repeat(5) {
                            delay(500)
                            println("inside second child launch: $it")
                        }
                    } catch (e: Exception) {
                        println("exception inside second child: ${e.message}")
                    }
                    println("inside second child end")
                    CoroutineScope(Dispatchers.IO).launch {
                        repeat(5) {
                            delay(500)
                            println("inside second child launch: $it")
                        }
                    }
                }
            }
        }
        job.join()
        delay(10000)
        println("end")
    }
}

fun caseTwo() {
    runBlocking {
        val job = launch {
            try {
                coroutineScope {
                    launch {
                        delay(1000)
                        throw RuntimeException()
                    }
                    launch {
                        delay(2000)
                        println("second child finished")
                    }
                }
            } catch(e: Exception) {
                println("catch exception")
            }
            delay(3000)
            println("end of parent")
        }
        job.join()
    }
    println("end of all")
}

fun caseOne() {
    runBlocking {
        val superScope = SupervisorJob()
        val job1 = CoroutineScope(superScope).launch {
            CoroutineScope(Dispatchers.IO).launch {
                delay(3000)
                println("independent coroutine")
            }
            launch {
                delay(1000)
                throw RuntimeException()
            }
            launch {
                delay(2000)
                println("printing end")
            }
            delay(3000)
            println("parent end")
        }
        job1.join()
        delay(4000)
        println("end of job1")
    }
    println("end of all")
}


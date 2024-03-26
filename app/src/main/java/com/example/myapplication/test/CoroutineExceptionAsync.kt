package com.example.myapplication.test

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.lang.RuntimeException

/**
 * ceh only can avoid crash but not cancellation. To avoid cancellation you must use supervisor job (in case of launch)
 *
 * without ceh also app wont't crash until we use await over async but it will cancelled all coroutine withing its parent scope(in case of async)
 * to avoid it again use supervisor job with ceh. You can try without ceh, only use supervisor job and don't use await.
 */
fun main() {
    caseAsync1()
}

fun caseAsync1() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("caught exception: $throwable")
    }
    runBlocking {
        val rootScope = CoroutineScope(Job())
        rootScope.async {
            coroutineScope {
                try {
                    val defered = async {
                        throw RuntimeException("throw exception")
                    }
                    defered.await()
                } catch (exception: Exception) {

                }
            }
        }.join()
    }
}
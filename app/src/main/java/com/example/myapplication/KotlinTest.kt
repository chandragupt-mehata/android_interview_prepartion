package com.example.myapplication

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() {
    val anonymousFun = fun(b: Int, a: Int): Int{
        return 10
    }
    val z = fun() {

    }
    z.invoke()

    val zz = object : TestInter{
        override fun test() {
            TODO("Not yet implemented")
        }
    }
    var x = 10
    var y = 10
    println("before primitive variables are ${x === y} and x hash code is: ${x.hashCode()}")
    x = 15
    println("both primitive variables are ${x === y} and x hash code is: ${x.hashCode()}")
    println("x is: $x and y is: $y")

    //
    val list = mutableListOf<String>("123", "45")
    list.apply {
        this.add(0, "")
    }
    val intList = mutableListOf(3, 5, 9)
    val result = intList.reduce { acc, num -> acc + num }
    println("fold result : $result")

    val lambda: (String, String) -> String = {abc, def ->
         ""
    }
}

interface TestInter {
    fun test()
}

class TestImpl: TestInter {
    override fun test() {
        TODO("Not yet implemented")
    }
}

fun higherOrderFunction(function: (String) -> Unit) {
    function("")
}

fun callerFunctionWhichNeedResult() {
    higherOrderFunction {
        val result = it
    }
}

suspend fun suspendWrapper() {
    suspendCancellableCoroutine<String>  { continuation ->
        higherOrderFunction {
            continuation.resume(it)
        }
    }
}

suspend fun callerFunctionWhichNeedResultInSuspendedWay() {
    val result = suspendWrapper()
}

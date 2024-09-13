package com.example.myhiltapplication.common

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Stack
import java.util.concurrent.atomic.AtomicInteger
import kotlin.RuntimeException
import kotlin.concurrent.thread
import kotlin.coroutines.resume

class TestIt {

    fun testInfixFunction() {
        val p1 = Point(0, 1)
        val p2 = Point(9, 4)
        val z = p1 plus p2
        val isValid = p1.isValid(p1)
        val isValidExtension = p1 isEqual p2
    }

}

class Point(val x: Int, val y: Int) {
    infix operator fun plus(second: Point): Point {
        return Point(second.x + this.x, second.y + this.y)
    }

    infix fun isValid(input: Point): Boolean {
        return true
    }
}

infix fun Point.isEqual(input: Point): Boolean {
    return true
}

class GetterSetter {
    var point: Point = Point(1, 2)
        get() = Point(1, 2)
        set(value) {
            field = value
        }
}

suspend fun main() {
    /*val getterSetter = GetterSetter()
    getterSetter.point = Point(3, 4)
    println("value is: ${getterSetter.point.x}")

    plusMinus(intArrayOf(-4, 3, -9, 0, 4, 1))
    testSync()

    //time
    println(timeConversion("01:00:05PM"))
    callWrapper()*/
    //testFlow()
    //testCatchException()
    //testException()
    val str1: String = String("hello".toCharArray())
    // str1 = "hello"
    var str2: String = String()
    str2 = "hello"
    println("hash code: ${str1.hashCode()} and ${str2.hashCode()}")
    println("references: ${System.identityHashCode(str1)} and ${System.identityHashCode(str2)}")
    println("are equals: ${str1 == str2}")
    println("are same object: ${str1 === str2}")

    /*coroutineScope {
        val flowObject = getFlow().stateIn(this)
        launch {
            flowObject.collect {
                println("inside first: $it")
            }
        }
        delay(300)
        launch {
            flowObject.collect {
                println("inside second: $it")
            }
        }
    }*/

    /*coroutineScope {
        task1()
        task2()
    }
    val testClass1 = TestClass("hello")
    val testClass2 = TestClass("hello")
    println("are both objects equals: ${testClass1 == testClass2}")

    val value1: Int? = 12
    val value2: Int? = 12
    println("are both primitive equals: ${value1 === value2}")*/

    val result = squareHOF(2) {
        it * it
    }
    println(backspaceCompare("#CA#A##", "CA##A"))
    println(backspaceCompare("#CA#A###EC#", "CA##A#E"))  // Output: true

    //val collectValue = mutableStateValue.
    val result1 = testDispatcher()
    println("testDispatcher result is :$result1")
}

suspend fun testDispatcher(): String {
    var value = "initial"
    CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        value = "updated"
    }.join()
    return value
}

var mutableStateValue = mutableStateOf("hello")

suspend fun updateState() {
    for (item in 1..5) {
        delay(100)
        mutableStateValue.value = item.toString()
    }
}

fun removeHash(inputString: String): String {
    val result = Stack<Char>()
    for(i in 0..inputString.length - 1) {
        val currentChar = inputString[i]
        if(currentChar == '#') {
            if(result.isNotEmpty()) result.pop() //
        } else {
            result.add(currentChar)
        }
    }
    return result.joinToString()
}


fun backspaceCompare(A: String, B: String): Boolean {
    fun processString(s: String): String {
        val stack = mutableListOf<Char>()
        for (char in s) {
            if (char != '#') {
                stack.add(char)
            } else if (stack.isNotEmpty()) {
                stack.removeAt(stack.size - 1)
            }
        }
        return stack.joinToString("")
    }

    return removeHash(A) == removeHash(B)
}


fun squareHOF(value: Int, calculator: (Int) -> Int): Int {
    return calculator(value)
}

suspend fun task1() {
    while (true) {
        println("hello inside first coroutine")
    }
}

suspend fun task2() {
    for (item in 1..10) {
        delay(100)
        println("hello inside second coroutine")
    }
}

class TestClass(val str: String)

fun getFlow(): Flow<String> {
    return flow<String> {
        for (i in 1..5) {
            delay(100)
            emit(i.toString())
        }
    }
}

open class NetworkState {
    object Loading : NetworkState()
    data class Success(val data: String) : NetworkState()
    data class Error(val error: Throwable) : NetworkState()
    object NoConnection : NetworkState() // New subclass added
}

fun handleNetworkState(state: NetworkState) {
    when (state) {
        is NetworkState.Loading -> println("Loading...")
        is NetworkState.Success -> println("Data: ${state.data}")
        is NetworkState.Error -> println("Error: ${state.error.message}")
        // Missing case for NetworkState.NoConnection
    }
}

class MyOperator {

    fun performOperator(input: String) {
        val result = input minus1 ""
        val result1 = input + ""
    }

    infix fun String.minus1(argument: String): String {
         return "dummy"
    }

    operator fun plus(argument: String): String {
        return "dummy"
    }
}

fun plusMinus(arr: IntArray): Unit {
    // Write your code here
    val positiveNumbers = arr.filter{it>0}
    val negativeNumbers = arr.filter{it<0}
    val zeroNumbers = arr.filter{it == 0}

    val p1 = String.format("%.2f", positiveNumbers.size.toDouble() / arr.size)
    val n1 = String.format("%.2f", (negativeNumbers.size.toDouble() / arr.size))
    val z1 = String.format("%.2f", (zeroNumbers.size.toDouble() / arr.size))
    println(p1)
    println(n1)
    println(z1)
}

fun testSync() {
    val atomicInteger = AtomicInteger()
    val assigningValue = AtomicInteger(0)
    val t1 = thread {
        for (i in 1..100) {
            var value = assigningValue.getAndIncrement()
            value++
            assigningValue.set(value)
            atomicInteger.set(assigningValue.get())
            Thread.sleep(10)
        }
    }
    val t2 = thread {
        for (i in 1..100) {
            assigningValue.getAndIncrement()
            atomicInteger.set(assigningValue.get())
            Thread.sleep(10)
        }
    }
    t1.join()
    t2.join()
    println("testSync value is : ${atomicInteger.get()}")
}

/*
 * Complete the 'timeConversion' function below.
 *
 * The function is expected to return a STRING.
 * The function accepts STRING s as parameter.
 */

fun timeConversion(s: String): String? {
    // Write your code here
    val outputFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
    val inputFormat = SimpleDateFormat("hh:mm:ssa", Locale.getDefault())
    val inputDate = inputFormat.parse(s)
    return inputDate?.let { outputFormat.format(it) }
}

fun lambda(internal: (Int) -> String) {
    //do something
    thread {
        Thread.sleep(2000)
        internal(9)
    }
}

fun caller() {
    lambda {
        it + 30
        ""
    }
}

suspend fun wrapperAlternateOfCaller(): Int {
    return suspendCancellableCoroutine<Int> {
        lambda { abc ->
            it.resume(abc)
            Thread.sleep(5000)
            println("after resuming")
            ""
        }
    }
}

suspend fun callWrapper() {
    val result = wrapperAlternateOfCaller()
    println("final result: $result")
}

fun testFlow() {
    runBlocking {
        launch {
            val res = channel.receive()
            delay(100)
            //channel.close()
            println("receiving closed in first launch: $res")
            for (value in channel) {
                println("inside loop")
                channel.receive()
            }
            channel.close()
        }
        /*launch {
            val res = channel.receive()
            println("receiving closed in second launch: $res")
        }
        launch {
            //channel.receive()
            //println("receiving closed in third launch")
        }*/
        listOf(0,1,2,3,4,5).forEach { it ->
            channel.send(it)
            println("sending closed: $it")
        }
        println("end")
    }
}

fun getflow(): Flow<Int> {
    var counter = 0
    return flow {
        while (counter < 10) {
            delay(100)
            println("value in emitter: $counter, and thread name is ${Thread.currentThread().name}")
            emit(counter++)
        }
    }
}

val channel: Channel<Int> = Channel()

suspend fun getExceptionThrowingFlow(): Flow<String> {
    return withContext(Dispatchers.IO) {
        var counter = 0
        flow {
            while (counter < 10) {
                delay(100)
                println("testCatchException")
                emit(counter.toString())
                counter++
                throw RuntimeException("Crashed")
            }
        }
    }
}

suspend fun testCatchException() {
    getExceptionThrowingFlow().conflate().catch {
        println("exception caught is : ${it.message}")
    }.collect {
        println("collected value is: $it")
    }
    println("end of test")
}

/**
 * Android 34 - Calling implicit intent only to exported component.
 * Foreground service type should be there.
 */

suspend fun lowestOne() {
    yield()
}

suspend fun lowerOne() {
    lowestOne()
}

suspend fun middleOne() {
    lowerOne()
}

suspend fun outerOne() {
    middleOne()
}

fun fileAccess(context: Context) {
    val file = context.getExternalFilesDirs("")
}

suspend fun testException() {
    try {
        throwException()
    } catch (e: Exception) {
        println("its crashed with msg: ${e.message}")
    }
}

suspend fun throwException() {
    CoroutineScope(Dispatchers.IO).launch {
        coroutineScope {
            launch {
                launch {
                    delay(1000)
                    throw RuntimeException("crash me")
                }
            }
        }
    }.join()
}

private val _liveData = MutableLiveData<String>()
val liveData: LiveData<String> get() {
    return liveDataWithoutGet
}
val liveDataWithoutGet: LiveData<String> = _liveData

fun testIt() {
    //liveDataWithoutGet.value = ""
}



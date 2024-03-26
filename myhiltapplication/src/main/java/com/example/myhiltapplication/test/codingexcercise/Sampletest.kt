package com.example.myhiltapplication.test.codingexcercise

fun main() {
    //callingFunction()
    callingFunctionTwo()
}

inline fun higherOrderFun( function1: (Int, Int) -> Int) {
    println("start in higher order function")
    val result = function1(2, 3)
    println("result: $result")
}

fun callingFunction() {
    higherOrderFun { a, b ->
        val res = a + b
        println(res)
        res
    }
}

fun callingFunctionAfterInlining() {
    /*println("start in higher order function")
    val res = a + b
    println(res)
    res
    println("result: $result")*/
}


inline fun higherOrderFunction(aLambda: () -> Unit) {
    aLambda()
    println("I won't be executed when you call callingFunction()")
}

/**
 * Also, when using inline functions, you will be able to return from the lambda which in turn will return from the calling function.
 * This is usually called non-local control flow.
 * The crossinline marker is used to mark lambdas that mustn’t allow non-local returns. i.e. first
 */
fun callingFunctionTwo() {

    higherOrderFunction {

        println("Non-local control flow")

        return

    }
    println("Even I won't be executed when you call callingFunction())")

}


//************************
inline fun higherOrderFunctionTwo(crossinline aLambda: () -> Unit) {

    normalFunctionTwo {
        aLambda() //must mark aLambda as crossinline to use in this context.
    }

}

fun normalFunctionTwo(aLambda: () -> Unit) {

    return

}

fun callingFunctionThree() {

    higherOrderFunctionTwo {

        return@higherOrderFunctionTwo //Error. Can't return from here.
    }

}



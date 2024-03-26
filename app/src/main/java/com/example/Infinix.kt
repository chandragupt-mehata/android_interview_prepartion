package com.example

fun main() {
    val result = "" infixA "hi"
    println("result is: $result")

    //val normalFunVariable: () -> Unit = ::normalFun
    /*val normalFunVariable: (String) -> String = { abc ->
        ""
    }*/
    val dummyFunVariable: String.(String) -> String = {
        "dummy"
    }
    val result1 = "".dummyFunVariable("")
    println("result1 is : $result1")

    val list = listOf<Int>()
    //list.all {  }

    val lambda1: (String, String) -> Unit = { abc: String, def: String ->
        println("hello")
    }

    val lambda2: (DestructurableDataClass) -> Unit = { (abc: String, def: String) ->
        println("hello")
    }
}

infix fun String.infixA(str: String): String {
    println("inside infixA A")
    return "result"
}

fun String.dummyFun(abc: Int): String {
    return ""
}

fun normalFun(str: String): String {
    return ""
}

data class DestructurableDataClass(val first: String, val second: String)

class NonDestructurableDataClass(val first: String, val second: String)
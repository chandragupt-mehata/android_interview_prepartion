package com.example.myapplication.test

import java.lang.RuntimeException

fun main() {
    val str by lazy {
        println("hello")
        "what"
    }
    println(str)
    var abc: Any = ""
    abc = "c"
    abc = 12345
}

fun nothingFunction(): Nothing {
    throw RuntimeException()
}
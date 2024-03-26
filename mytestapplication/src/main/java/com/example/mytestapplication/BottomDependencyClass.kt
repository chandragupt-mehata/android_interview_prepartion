package com.example.mytestapplication

class BottomDependencyClass {

    fun innerFunction() {
        println("hi how are you: innerFunction")
    }
}

fun main() {
    println("${5 == 5} and ${5 === 5}")
}
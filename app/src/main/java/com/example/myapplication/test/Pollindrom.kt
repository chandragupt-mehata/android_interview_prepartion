package com.example.myapplication.test

fun main() {
    println(isPollindrom(123216))
}

fun isPollindrom(input: Int): Boolean {
    val inputStr = input.toString()
    if (inputStr.length % 2 == 0) {
        return false
    }
    for (index in 0 until inputStr.length/2) {
        if (inputStr[index] != inputStr[(inputStr.length - 1) - index]) {
            return false
        }
    }
    return true
}
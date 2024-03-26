package com.example.myapplication.test

fun main() {
    returnStatement()
    println(secondSmallestNumber(intArrayOf(10, 1, 9, 1, 1, 2, 2, 3, 4, 5)))
}

fun returnStatement(): Int {
    for (i in 1..10) {
        if (i == 5) {
            break
        }
        println("start value: $i")
    }
    println("end")
    return -1
}

fun secondSmallestNumber(input: IntArray): Int {
    input.sort()
    val firstSmallest = input.first()
    for (index in 1..input.size) {
        if (firstSmallest != input[index]) {
            return input[index]
        }
    }
    return -1
}
package com.example.myapplication.test

fun main() {
    println(getMissingNumber(intArrayOf(1, 0)))
}

fun getMissingNumber(input: IntArray): Int {
    /*for (index in 0..input.size) {
        if (!input.contains(index)) {
            return index
        }
    }*/
    val sum = input.size * (input.size + 1) / 2
    val currentSum = input.sum()
    return sum - currentSum
}
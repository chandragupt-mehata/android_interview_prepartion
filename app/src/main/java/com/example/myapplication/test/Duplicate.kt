package com.example.myapplication.test

import java.util.stream.IntStream




fun main() {
    //findDuplicate(intArrayOf(1, 2, 3, 4, 5, 1))
    println(findDuplicateNew(intArrayOf(1, 2, 3, 4, 5)))
}

fun findDuplicate(input: IntArray) {
    val inputSet = mutableSetOf<Int>()
    input.forEach {
        if (inputSet.contains(it)) {
            println("duplicate number is : $it")
            return
        } else {
            inputSet.add(it)
        }
    }
    println("no duplicate founds")
    return
}

//loks wrong one
fun findDuplicateNew(nums: IntArray): Int {
    val actual_sum = IntStream.of(*nums).sum()
    val expected_sum = nums.size * (nums.size - 1) / 2
    return actual_sum - expected_sum
}
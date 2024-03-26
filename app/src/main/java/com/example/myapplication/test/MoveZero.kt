package com.example.myapplication.test

fun main() {
    println(moveZero(intArrayOf(0, 1, 0, 3, 12)))
}

//1,2,0,4
fun moveZero(input: IntArray) {
    var zeroCount = 0
    for (index in input.indices) {
        if (input[index] == 0) {
            zeroCount ++
        } else if (zeroCount > 0) {
            input[index - zeroCount] = input[index]
            input[index] = 0
        }
    }
    println(input.toList())
}

internal class Solution {
    fun moveZeroes(nums: IntArray) {
        var nonZeroIndex = 0
        for (i in nums.indices) {
            if (nums[i] != 0) {
                if (i != nonZeroIndex) {
                    val temp = nums[nonZeroIndex]
                    nums[nonZeroIndex] = nums[i]
                    nums[i] = temp
                }
                nonZeroIndex++
            }
        }
    }
}
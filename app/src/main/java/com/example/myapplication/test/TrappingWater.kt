package com.example.myapplication.test

fun main() {
    println(trap(intArrayOf(4, 2, 0, 3, 2, 5)))
}

fun trap(height: IntArray): Int {
    var trapedWater = 0
    for (i in 1..height.size - 2) {
        //for left bar
        var leftBar = height[i]
        for (j in 0 until i) {
            leftBar = maxOf(height[j], leftBar)
        }
        var rightBar = height[i]
        for (j in i + 1 until height.size) {
            rightBar = maxOf(rightBar, height[j] )
        }
        val waterLever = minOf(leftBar, rightBar)
        trapedWater += (waterLever - height[i])
    }
    return trapedWater
}
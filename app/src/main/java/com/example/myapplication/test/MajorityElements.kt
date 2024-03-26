package com.example.myapplication.test

fun main() {
    println(findMajorityElements(intArrayOf(1, 2, 3, 2, 4, 2)))
}

fun findMajorityElements(input: IntArray): Int {
    val map = HashMap<Int, Int>()
    input.forEach {
        if (map.containsKey(it)) {
            map[it] = map.getValue(it) + 1
        } else {
            map[it] = 1
        }
    }
    var result: Int? = null
    map.entries.forEach {
        result = if (result == null) {
            it.value
        } else {
            maxOf(result!!, it.value)
        }
    }
    return map.keys.find { map[it] == result }!!
}

/*
class Solution {
    fun majorityElement(nums: IntArray): Int {
        var count = HashMap<Int, Int>()
        for(n in nums) {
            count[n] = count.getOrDefault(n, 0) + 1
            if (count[n]!! > nums.size/2) return n
        }
        return -1
    }
}*/

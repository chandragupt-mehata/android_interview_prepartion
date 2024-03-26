package com.example.myhiltapplication.test.codingexcercise

/**
 * 0, 1, 1, 2, 3, 5, 8, 13, 21
 */
fun main() {
    //println(noOfWays(5))
    //println(mergerTwoSortedArray(intArrayOf(1,2,3,0,0,0), intArrayOf(2,5,6), 3, 3).toList())
    println(removeDuplicates1(intArrayOf(1,1,2)))
}

fun noOfWays(n: Int): Int {
    if (n <= 3) return n

    var left = 0
    var right = 1
    for (i in 0 until n) {
        val temp = right
        right += left
        left = temp
    }

    return right
}
// 5, 10
fun mergerTwoSortedArray(arr1: IntArray, arr2: IntArray, m: Int, n: Int): IntArray {
    var counter = 0
    for (index in m until arr1.size) {
        if (counter < n) {
            arr1[index] = arr2[counter]
            counter ++
        }
    }
    arr1.sort()
    return arr1
}

fun removeDuplicates1(nums: IntArray): Int {
    nums.distinct().let {
        it.forEachIndexed { index, value ->
            nums[index] = value
        }
        return it.size
    }
}
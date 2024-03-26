package com.example.myhiltapplication.test.codingexcercise

fun main() {
    println(solutionThree(intArrayOf(0,2,2,1,1)))
}

fun solution(A: IntArray): Int {
    A.sort()
    var prev: Int? = A.firstOrNull { it >= 0 } ?: return 1
    if (prev == A.last()) return prev + 1
    for (index in A.indexOf(prev!!) + 1 until A.size) {
        if (A[index] != prev + 1 && A[index] != prev) {
            return prev + 1
        }
        prev = A[index]
    }
    return prev + 1
}

//for first missing positive starting from 1
fun solutionTwo(A: IntArray): Int {
    val list = A.distinct().sorted()
    list.firstOrNull { it > 0 } ?: return 1
    var index = 1
    for (item in list) {
        if (item > 0) {
            if (item != index) {
                return index
            }
            index ++
        }
    }
    return index
}

//for first missing positive starting from 1
fun solutionThree(A: IntArray): Int {
    val hashMap = HashMap<Int, Boolean>()
    var minPositiveNum = 1
    A.forEach {
        if (it > 0) {
            hashMap[it] = true
        }
    }
    while (true) {
        if (!hashMap.containsKey(minPositiveNum)) {
            return minPositiveNum
        }
        minPositiveNum ++
    }
}

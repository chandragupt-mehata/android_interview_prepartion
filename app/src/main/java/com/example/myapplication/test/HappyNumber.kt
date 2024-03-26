package com.example.myapplication.test

/**
 * https://leetcode.com/problems/happy-number/solutions/4102556/simple-solution-by-the-rule-of-thumb/?envType=featured-list&envId=top-interview-questions?envType=featured-list&envId=top-interview-questions
 */
fun main() {
    //println(isHappyNumber(2))
    val a = 1;
    val b= 2;
    val c= "aaa"
    val d=4
    val res = (a + b).toString() + c + d.toString()
    println(res)
    println(doesContainDuplicate(intArrayOf(1, 2, 3, 4,5 )))
}

fun isHappyNumber(input: Int): Boolean {
    return validateNumber(input.toString())
}

var counter = 0

fun validateNumber(str: String): Boolean {
    if (counter >= 15) {
        return false
    }
    return if (str.toInt() == 1) {
        true
    } else {
        var sum = 0
        counter ++
        str.forEach {
            sum += it.digitToInt() * it.digitToInt()
        }
        if (sum == 1) {
            true
        } else {
            validateNumber(sum.toString())
        }
    }
}

fun doesContainDuplicate(input: IntArray): Boolean {
    val distictArray = input.distinct()
    return distictArray.size != input.size
}
package com.example.myhiltapplication.test.codingexcercise

import com.example.myhiltapplication.common.MySealedClass1
import java.lang.RuntimeException

fun main() {
    println(twoSum(intArrayOf(3, 2, 4), 6).toList())
    println(romanToInt("XIV"))
}

fun twoSum(input: IntArray, sum: Int): IntArray {
    val set = mutableMapOf<Int, Int>()
    input.forEachIndexed {
        index, item ->
        if (set.isEmpty()) {
            set[item] = index
        }
        else {
            if (set.containsKey(sum - item)) {
                return intArrayOf(set[sum - item]!!, index)
            } else {
                set[item] = index
            }
        }
    }
    return intArrayOf()
}

//xiv = 10 + 4 = 14
fun romanToInt(romanString: String): Int {
    var sum = 0
    romanString.forEachIndexed() { index, char ->
        val currentValue = char.toIntValue()
        if (romanString.lastIndex > index) {
            val nextValue = romanString[index + 1].toIntValue()
            if (currentValue >= nextValue) {
                sum += currentValue
            } else {
                sum -= currentValue
            }
        } else {
            sum += currentValue
        }
    }
    return sum
}

fun Char.toIntValue(): Int {
    when (this) {
        'I' -> return 1
        'V' -> return 5
        'X' -> return 10
        'L' -> return 50
        'C' -> return 100
        'D' -> return 500
        'M' -> return 1000
    }
    throw RuntimeException("can not convert into int")
}

class Test: MySealedClass1("") {

}


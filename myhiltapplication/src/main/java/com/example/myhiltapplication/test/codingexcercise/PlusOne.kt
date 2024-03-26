package com.example.myhiltapplication.test.codingexcercise


fun main() {
    println(plusOne(intArrayOf(9,8,7,6,5,4,3,2,1,0)).toList())
}

fun plusOne(input: IntArray): IntArray {
    /*val sb = StringBuilder()
    input.map {
        sb.append(it)
    }
    val intValue = sb.toString().toBigInteger().toInt() + 1
    val result = mutableListOf<Int>()
    intValue.toString().forEach {
        result.add(it.toString().toInt())
    }
    println("str: $intValue")*/

    for (index in input.size - 1 downTo 0) {
        if (input[index] < 9) {
            input[index] ++
            return input
        }
        input[index] = 0
    }
    val newIntArray = IntArray(input.size + 1) {
        0
    }
    input.forEachIndexed { index, value ->
        newIntArray[index + 1] = value
    }
    newIntArray[0] = 1
    return newIntArray
}

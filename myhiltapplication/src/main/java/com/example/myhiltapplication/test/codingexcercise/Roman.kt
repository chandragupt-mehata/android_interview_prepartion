package com.example.myhiltapplication.test.codingexcercise

class RomanSolution {
    fun romanToInt(str: String): Int {
        val romanNumerals = mapOf('I' to 1,
            'V' to 5,
            'X' to 10,
            'L' to 50,
            'C' to 100,
            'D' to 500,
            'M' to 1000)

        var result = 0

        str.mapIndexed {
            index, value ->
            val current = romanNumerals[value]
            if ((index + 1) < str.length) {
                val nextValue = romanNumerals[str[index + 1]]
                if (current!! >= nextValue!!) {
                    result += current
                } else {
                    result -= current
                }
            } else {
                if (current != null) {
                    result += current
                }
            }
        }
        return result
    }
}

fun main() {
    val romanSolution = RomanSolution()
    println(romanSolution.romanToInt("VIIVI"))
}
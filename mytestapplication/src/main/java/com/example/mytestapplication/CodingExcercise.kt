package com.example.mytestapplication

/*
* Given an array with temperatures in Celsius and Fahrenheit scale (e.g., -2C, 23F, etc).
* Write code that calculates the average temperature, then subtracts it from the presented values.
* Return the results in the same format, preserving Celsius and Fahrenheit scale.
* Formula for conversion from Fahrenheit to Celsius: C = (F - 32) * 5/9
* Example:
* input: ["10C", "9C", "7C", "4.5C", "-2C", "23F", "0C", "-1.5C", "41F", "3C"]
* average temperature: 3C
* output: ["7C", "6C", "4C", "1.5C", "-5C", "17.6F", "-3C", "-4.5C", "35.6F", “0C"]
*/
// 23f = -5
// 41f = 5
// [7.0C, 6.0C, 4.0C, 1.5C, -5.0C, -14.400002F, -3.0C, -4.5C, 3.5999985F, 0.0C]
// [7.0C, 6.0C, 4.0C, 1.5C, -5.0C, -14.400002F, -3.0C, -4.5C, 3.5999985F, 0.0C]

val temperatures = arrayOf("10C", "9C", "7C", "4.5C", "-2C", "23F", "0C", "-1.5C", "41F", "3C")

fun recalculate(temperatures: Array<String>): Array<String> {
    // solution
    val totalSum = calculateTotalSum(temperatures)
    val avg: Float = totalSum / temperatures.size
    val avgFloat: Float = (avg * 9 / 5.0F) + 32
    temperatures.forEachIndexed {
        index, item ->
        if (item.contains("C")) {
            temperatures[index] = (item.tempValueInCentigrade() - avg).toString() + "C"
        } else {
            temperatures[index] = (item.tempValueInFahrenheit() - avgFloat).toString() + "F"
        }
    }
    return temperatures
}

fun calculateTotalSum(temperatures: Array<String>): Float {
    return temperatures.map {
        it.tempValueInCentigrade()
    }.reduce { acc, fl -> acc + fl }
}

fun String.tempValueInCentigrade(): Float {
    return if (this.last() == 'C') this.dropLast(1).toFloat()
    else {
        ((this.dropLast(1).toFloat()) - 32) * 5/9.0F
    }
}

fun String.tempValueInFahrenheit(): Float {
    return if (this.last() == 'F') this.dropLast(1).toFloat()
    else {
        ((this.dropLast(1).toFloat()) * 9 / 5) + 32
    }
}

fun main() {
    println(recalculate(temperatures).toList())
}
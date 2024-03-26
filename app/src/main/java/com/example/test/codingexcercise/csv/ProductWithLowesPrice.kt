package com.example.test.codingexcercise.csv


private val csvString = """
Product, Price
Apple,1.5 
Banana, 0.75   
Orange,1.0
""".trimIndent()

fun main() {
    println(getLowestPriceProduct())
}

fun getLowestPriceProduct(): MutablePair<String, Double> {
    val data = csvString.lines()
    val pair = MutablePair("", Double.MAX_VALUE)
    if (data.isNotEmpty()) {
        data.drop(1).map {
            val (name, price) = it.split(",")
            if (price.toDoubleValue() < pair.second) {
                pair.first = name
                pair.second = price.toDouble()
            }
        }
    }
    return pair
}

fun getLowestPriceProduct2(): String? {
    val data = csvString.lines()
    if (data.isNotEmpty()) {
        val result = data.drop(1).minByOrNull {
            it.split(",")[1].toDoubleValue()
        }
        println(result)
        return result
    }
    return null
}

fun String.toDoubleValue(): Double {
    return when {
        isNotBlank() -> toDouble()
        else -> 0.0
    }
}
package com.example.test.codingexcercise.csv

/**
 * You have a CSV string representing inventory data in the format "Product, Stock".
 * Write a Kotlin function to parse this CSV string and return the product with the highest stock level.
 */

private val csvString = """
Product, Stock
Apple,100 
Banana, 150   
Orange,120
""".trimIndent()

fun main() {
    val data = csvString.lines()
    val highestStockContainedProduct = MutablePair("", 0)
    if (data.isNotEmpty()) {
        data.drop(1).map {
            val(name, stock) = it.split(",")
            if (stock.toNumber() > highestStockContainedProduct.second) {
                highestStockContainedProduct.first = name
                highestStockContainedProduct.second = stock.toNumber()
            }
        }
        println(highestStockContainedProduct)
    }
}

data class MutablePair<A, B>(var first: A, var second: B)

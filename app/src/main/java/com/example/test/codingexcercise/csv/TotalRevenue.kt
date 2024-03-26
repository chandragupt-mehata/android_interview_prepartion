package com.example.test.codingexcercise.csv

/**
 *
 * You have a CSV string representing sales data with the following format: "Product, Quantity, Price".
 * Write a Kotlin function to parse this CSV string and return the total revenue generated from the sales.
 *
 */

private val csvString = """
Product, Quantity, Price
Burgess Greasley,33,84 
Derwin Brunel,13,08   
Sheffie Spadotto,55,52
Courtney Fearnyhough,63,210
Melloney Stickens,19,979     
Ellery Geere,53,7 
Boone Malimoe,19,72 
Nikki Goodere,61,6 
Annabela Riddel,34,913  
""".trimIndent()

fun main() {
    if (csvString.lines().isNotEmpty()) {
        println(findTotalRevenue())
    }
}

private fun findTotalRevenueWithoutDataClass(): Int {
    return csvString
        .lines()
        .drop(1)
        .sumOf {
            val(_, quantity, price) = it.split(",")
            quantity.toNumber() * price.toNumber()
        }
}

private fun findTotalRevenue(): Int {
    return csvString
        .lines()
        .drop(1)
        .sumOf {
            val productDetails = it.parseCsv()
            (productDetails.price.toNumber() * productDetails.quantity.toNumber())
        }
}

fun String.toNumber(): Int {
    return when {
        isNotBlank() -> toInt()
        else -> 0
    }
}

private fun String.parseCsv(): ProductDetails {
    val mapping = listOf("name", "quantity", "price")
    val mappedValues = mapping.zip(split(",")).toMap()
    return ProductDetails(
        name = mappedValues["name"] ?: "",
        quantity = mappedValues["quantity"] ?: "",
        price = mappedValues["price"] ?: ""
    )
}

data class ProductDetails(val name: String, val quantity: String, val price: String)



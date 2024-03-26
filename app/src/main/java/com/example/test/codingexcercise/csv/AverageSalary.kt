package com.example.test.codingexcercise.csv

/**
 * You need to parse a CSV string representing employee salaries with the format "Name, Salary".
 * Write a Kotlin function to parse this CSV string and return the average salary of all employees.
 *
 */

private val csvString = """
Name, Salary
Burgess Greasley,50000
Derwin Brunel,60000
Burgess Greasley,75000
""".trimIndent()

fun main() {
    val data = csvString.lines()
    if (data.isNotEmpty()) {
        val result = data
            .drop(1)
            .map { it.split(",").last().toNumber() }
            .average()
        println("%.2f".format(result))
    }
}

fun avgSalary(): Double {
    return csvString.lines().drop(1).map { it.split(",").last().toNumber() }.average()
}
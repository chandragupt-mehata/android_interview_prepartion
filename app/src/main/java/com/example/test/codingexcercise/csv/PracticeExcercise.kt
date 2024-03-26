package com.example.test.codingexcercise.csv


private val csvString = """
Student, Subject, Grade
Burgess Greasley,Math,90 
Derwin Brunel,Science,85   
Burgess Greasley,Science,92
Derwin Brunel,Math,82
""".trimIndent()

fun main() {
    /**
     * https://hyperskill.org/learn/step/25060
     * minOrNull() returns the smallest element or null if there are no elements.
     *
     * maxOrNull() returns the largest element or null if there are no elements.
     *
     * minByOrNull() with a selector function returns the element with the smallest value.
     *
     * maxByOrNull() with a selector function returns the element with the largest value.
     *
     * minOfOrNull() with a selector function returns the smallest return value of the selector itself.
     *
     * maxOfOrNull() with a selector function returns the largest return value of the selector itself.
     *
     * minWithOrNull() returns the smallest element according to the comparator.
     *
     * maxWithOrNull() returns the largest element according to the comparator.
     *
     * minOfWithOrNull() returns the smallest selector return value according to the comparator.
     *
     * maxOfWithOrNull() returns the largest selector return value according to the comparator.
     */

    println(csvString.lines().drop(1).map { it.parseCsv() }.minOfOrNull { it.marks })
    println(csvString.lines().drop(1).map { it.parseCsv() }.minByOrNull { it.marks })
}

private fun String.parseCsv(): Student {
    val mapping = listOf("name", "subject", "marks")
    val mappedValues = mapping.zip(split(",")).toMap()
    return Student(
        mappedValues["name"] ?: "",
        mappedValues["subject"] ?: "",
        mappedValues["marks"]?.trim()?.toInt()
            ?: 0
    )
}

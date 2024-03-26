package com.example.test.codingexcercise.csv

/**
 * You are given a CSV string representing student grades in the format "Student, Subject, Grade".
 * Write a Kotlin function to parse this CSV string and return the average grade for each student.
 */

private val csvString = """
Student, Subject, Grade
Burgess Greasley,Math,90 
Derwin Brunel,Science,85   
Burgess Greasley,Science,92
Derwin Brunel,Math,82
""".trimIndent()

fun main() {
    println(calculateAverageGrade())
    println(calculateAverageGradeWithoutDataClass())
}

private fun calculateAverageGradeWithoutDataClass(): Map<String, Double> {
    return csvString
        .lines()
        .drop(1)
        .groupBy {
            it.split(",").first()
        }.mapValues {
            it.value.map {student ->
                student.split(",").last().trim().toInt()
            }.average()
        }
}

private fun calculateAverageGrade(): Map<String, Double> {
    return csvString
        .lines()
        .drop(1)
        .map { it.parseCsv() }
        .groupBy { it.name }
        .mapValues {
            it.value.map { student ->
                student.marks
            }.average()
        }
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

data class Student(val name: String, val subject: String, val marks: Int)

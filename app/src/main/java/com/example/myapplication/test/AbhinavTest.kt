package com.example.myapplication.test

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.lang.StringBuilder

fun mainc() {
    val string = "I love kotlin Programming"
    val words = string.split(" ")
    val reversedWords = words.reversed()
    val reversedString = reversedWords.joinToString(" ")
    println("output is : $reversedString")

    /*val output = StringBuilder()
    for (index in list.lastIndex downTo 0) {
        output.append(list[index])
        output.append(" ")
    }
    println("output is : $output")*/
}

/**
 * val csv = """ Name,Department,salary, Burgess Greasley,IT,65000 Derwin Brunel,Finance, 50000 Sheffie Spadotto,It, 75000 Courtney Fearnyhough,Finance, 70000 Melloney Stickens,HR, 45000 Ellery Geere,HR,65000 """.trimIndent() 1. convert string to list 2. remove header from list 3. calculate average of salary of each department  4. create map of average of salary with department   Output {It= 70000, Finance= 60000, HR=55000  }
 * 2, 3, 4, 5
 * 2, 3 = 2.5
 * 2.5 + 4 = 6.5/2
 * 6.5/2 + 5
 */

val csv = """
Name,Department,salary
Burgess Greasley,IT,65000
Derwin Brunel,Finance, 50000
Sheffie Spadotto,It, 75000
Courtney Fearnyhough,Finance, 70000
Melloney Stickens,HR, 45000
Ellery Geere,HR,65000
""".trimIndent()

fun mainxcxc() {
    val list = csv.split("\n")
    val newList = list.drop(1).reversed()
    val departmentMutableList = mutableListOf<Department>()
    for (item in newList) {
        val rowString = item.split(",")
        val department = Department(rowString[0], rowString[1], rowString[2].trim().toDouble())
        departmentMutableList.add(department)
    }
    println("mutable list is: $departmentMutableList")

    val map = mutableMapOf<String, List<Double>>()
    val targetMap = mutableMapOf<String, Double>()
    for (department in departmentMutableList) {
        if (map.containsKey(department.department.departmentName())) {
            map[department.department.departmentName()]?.let {
                val listNew = map[department.department.departmentName()]!!.toMutableList()
                listNew.add(department.salary)
                map[department.department.departmentName()] = listNew
            }
        } else {
            map[department.department.departmentName()] = mutableListOf(department.salary)
        }
    }
    map.forEach { (key, value) ->
        targetMap[key] = value.average()
    }
    println("map is: $targetMap")
}

fun String.departmentName(): String {
    return this.lowercase()
}

data class Department(val name: String, val department: String, val salary: Double) {
    companion object {
        fun fromString(list: List<String>) = Department(
            name = list[0],
            department = list[1],
            salary = list[2].toDouble()
        )
    }
}

fun main() {
    val list = csv.split("\n")
    val res = list.drop(1).map {
        Department.fromString(it.split(","))
    }.groupBy { it.department.lowercase() }.mapValues {
        it.value.map {department ->
            department.salary
        }.average()
    }
    println("res is : $res")
}
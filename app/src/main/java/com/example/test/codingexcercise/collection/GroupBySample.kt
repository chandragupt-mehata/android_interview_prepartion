package com.example.test.codingexcercise.collection

fun main() {
    val list = listOf(1, 2, 0, 3, 0, 7, 4, 8, 5)
    val groupByListOne = list.groupBy { it > 5 }
    println(groupByListOne)

    val groupByListTwo = list.groupBy { it == 5 }
    println(groupByListTwo)

    val groupTestDataClassList = listOf(
        GroupTestDataClass("abc"),
        GroupTestDataClass("def"),
        GroupTestDataClass("ghi"),
        GroupTestDataClass("abc"),
        GroupTestDataClass("ac")
    )
    println(groupTestDataClassList.groupBy { it.name })
}

data class GroupTestDataClass(val name: String)
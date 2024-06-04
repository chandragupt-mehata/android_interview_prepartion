package com.example.test.codingexcercise.collection

fun main() {
    val list = listOf(1, 2, 0, 3, 0, 2, 4, 2, 5)
    val groupByListOne = list.groupBy {
        //println("inside: it: $it")
        it
    }.map {
        println("inside: it: $it")
    }
    println(groupByListOne)

    val groupByListTwo = list.groupBy { it == 5 }
    println(groupByListTwo)

    val groupTestDataClassList = listOf(
        GroupTestDataClass("abc", 25),
        GroupTestDataClass("def", 55),
        GroupTestDataClass("ghi", 66),
        GroupTestDataClass("abc", 76),
        GroupTestDataClass("ac", 46)
    )
    println(groupTestDataClassList.groupBy { it.name })

    val groupTestDataClassListSecond = listOf(
        GroupTestDataClass("abc"),
        GroupTestDataClass("def"),
        GroupTestDataClass("ghi"),
        GroupTestDataClass("abc"),
        GroupTestDataClass("ac")
    )
    println(groupTestDataClassListSecond.groupBy { it.name })
}

data class GroupTestDataClass(val name: String, val marks: Int = 0)
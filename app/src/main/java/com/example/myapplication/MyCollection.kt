package com.example.myapplication

fun main() {
    val integerValue: Integer = Integer(42)
    val integerValue2: Integer = Integer(42)
    println("size is: integerValue ${integerValue == integerValue2}")

    /*val x = Inte
    val pair = Pair("", 9)
    val triple = Triple("", 9, "")
    var (a, b, c) = triple
    a = ""

    val arrays = arrayOf("abc", "def")
    val sizedArray = Array(10){9}
    println("size is: ${sizedArray[5]}")

    val list = listOf<String>("cvc", "cv")
    //list[0] = ""
    val mutableList = list.toMutableList()
    println("are list same: ${list === mutableList}")

    val x = 10
    val y = 10
    println("check it: ${x == y} and ${x === y}") // auto boxing perform on primitive data type values

    for (index in 0..list.lastIndex) {

    }

    for (item in list) {

    }

    for ((index, item) in list.withIndex()) {

    }

    //map
    val map = mapOf(
        1 to mutableListOf<String>("cool"),
        2 to mutableListOf<String>("why")
    )
    //map[1] = mutableListOf<>()
    map[1]?.add("")
    for (keys in map.keys) {

    }
    for (values in map.values) {

    }
    for (item in map) {
        val (key, value) = item
    }
    for ((key, value ) in map) {

    }


    // set
    val set = setOf("", "x", "y", "c")

    //hof
    val lambda: (Int, Int) -> Int = {x, y -> x + y}
    val yx = ::passMe
    yx()
    val listNew = arrayOf("x", "y", "z")
    listNew.forEach(::passMeWithArgument)

    //hof practice
    val lotOfDwarves = listOf(
        listOf("yabc", "whi", "hwat", "jkl"),
        listOf("why", "def", "ghi")
    )
    val (firstList, secondList) = lotOfDwarves.flatMap {
        it.filter { items ->
            items.length < 10
        }
    }.sortedBy { it.length }.partition {
        it.first() < 'm'
    }
    println("result is: firstList $firstList and secondList: $secondList")


    //average ratings
    val appRatings = mapOf(
        "Calendar" to arrayOf(1, 2, 5),
        "Book" to arrayOf(7, 3, 4, 6)
    )
    val averageRatings = mutableMapOf<String, Double>()
    appRatings.forEach {
        val total = it.value.sum()
        val newTotal = it.value.reduce{
            a, b -> a - b
        }
        println("total is : $total and newTotal: $newTotal")
    }*/

    val list = listOf<Int>(1, 2, 3, 4, 5)
    val result = list.reduce {
        a, b ->
        println("values are: $a and $b")
        a + b
    }
    println("result is: $result")

    val array1 = Array<Any>(5) {

    }
    array1[0] = ""
    array1[0] = 2
    val array2 = arrayOf(1, "")
    val list1 = listOf(1, "")
    val hof: fnStringString = { abc: String, x: String -> "" }

    val str = "Hi this is me. How are you."
    val res = str.split(".").reversed().drop(1).joinToString(separator = ". ", postfix = ".")
    println("result is: $res")
}

typealias fnStringString = (String, String) -> String


fun passMe() {
    println("hello inside passMe")
}

fun passMeWithArgument(string: String) {
    println("hello inside passMe")
}

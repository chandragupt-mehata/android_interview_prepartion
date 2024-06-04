package com.example.myapplication.test

/*
Design a Credit Card Parser function that will check if a credit card number is valid or not.
Return the name of the issuer if the card is valid. Return null if invalid.
Validity is determined by two values and two criteria.
Value 1: The card number. Example: "3796-1111-2222-1122"
Value 2: The expiration date. Example: "11/22"
Criteria 1: The first four digits identify match an issuer from the following set:
-- ACME: starts with 1121
-- ALFA: starts with 1111
-- AMEX: starts with 3796
Criteria 2: The MM/YY expiration date matches the last four digits.
Example: "3796-1111-2222-1122" and "11/22" is valid. Return "Amex".
*/
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/**
 * //vZio interview questions
 * Please find the below question asked in  Vizio Interview :
 * Past project experience ?
 * What is MVVM?
 * How we can make network call?
 * In the case of multiple module project what will be the scope of singleton?
 * What is inline, crossline in kotlin?
 * How to check the type of generics at runtime?
 * Why compose is better ?
 * How to call coroutine from compose?
 * What is side effect? How these side effect behave at the time of configuration changes?
 * How to save UI state?
 * In the case of system initiated process death how you can persist the data?
 * Where you used Hot flow and cold flow in your project?
 * Difference between Hot Flow and Cold flow?
 * Difference between shared flow and state flow?
 * Why data flow in compose is unidirectional?
 * How you can share the data between two screen ?
 * How view model handle configuration changes and system initiated process death?
 * What is shared view model?
 * How you implement shared view model for two screens?
 * What is live data?
 * What is singleton design pattern?
 * Where you used singleton in your project?
 * Asked follow up questions(scenario based) on the answers.
 * problem solving question
 * /* Given a sorted array I/p  = [-2,-1,0,2,3] output will be
 * o/p = [0,1,4,4,9]
 * with the lowest time complexity */
 */
fun main25() {
    val result = CardParser.validate("3796-9999-8888-1216", "12/56")
    println(result)
}

object CardParser {
    fun validate(number: String, date: String): String? {
        return when {
            isValidDate(date = date) -> getCardTypeName(number)
            else -> null
        }
    }

    private val map = mapOf(
        Pair("3796", "AMEX"),
        Pair("1111", "ALFA"),
        Pair("1121", "ACME")
    )

    private val monthDateMap = mapOf(
        Pair(1, 1..31),
        Pair(2, 1..29),
        Pair(3, 1..31),
        Pair(4, 1..30),
        Pair(5, 1..31),
        Pair(6, 1..30),
        Pair(7, 1..31),
        Pair(8, 1..31),
        Pair(9, 1..30),
        Pair(10, 1..31),
        Pair(11, 1..30),
        Pair(12, 1..30),
    )

    private fun getCardTypeName(number: String): String? {
        var cardTypeName: String? = null
        val numList = number.split("-")
        if (numList.size == 4) {
            cardTypeName = map[numList.first()]
        }
        return cardTypeName
    }

    private fun isValidDate(date: String): Boolean {
        val dateList = date.split("/")
        if (dateList.size == 2) {
            val (month, dateV) = dateList.map { it.toIntOrNull() }
            return dateV != null && monthDateMap[month]?.contains(dateV) == true
        }
        return false
    }

}

fun main() {
    for (i in 1..1000000) {
        val thread = Thread(Runnable {

        })
        thread.start()
    }
    /*println(
        slot(
            arrayOf(
                intArrayOf(1, 3),
                intArrayOf(2, 6),
                intArrayOf(8, 10),
                intArrayOf(15, 18)
            )
        ).toList().forEach {
            println("value is: ${it[0]} and ${it[1]}")
        }
    )*/
}
fun slotT(input: Array<IntArray>): Array<IntArray> {
    var resultArray = arrayListOf<IntArray>()
    /*input.reduce(){
            a,b ->
// if(a[1] < b[0])
        resultArray[0] = intArrayOf(a[0],a[1])
    }*/
    var temp = intArrayOf(Int.MAX_VALUE, Int.MIN_VALUE)
    input.reduce {
        a, b ->
        if (a[1] < b[0]) {
            temp = intArrayOf(a[0], a[1])
            resultArray.add(temp)
        } else {
            temp = intArrayOf(minOf(a[0], b[0]), maxOf(a[1], b[1]))
            //resultArray.add(intArrayOf(a[0], a[1]))
        }
        b
    }
    return input
}

fun slot(input: Array<IntArray>): Array<IntArray> {
    val result = mutableListOf<IntArray>()
    var start = input[0][0]
    var end = input[0][1]

    for (i in 1 until input.size) {
        val interval = input[i]
        if (interval[0] <= end) {
            // Merge overlapping intervals
            end = maxOf(end, interval[1])
        } else {
            // Add non-overlapping interval to the result
            result.add(intArrayOf(start, end))
            start = interval[0]
            end = interval[1]
        }
    }
    result.add(intArrayOf(start, end))
    return result.toTypedArray()
}


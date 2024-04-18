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
fun main() {
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

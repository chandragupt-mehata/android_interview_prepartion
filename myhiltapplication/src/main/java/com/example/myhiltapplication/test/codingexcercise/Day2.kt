package com.example.myhiltapplication.test.codingexcercise

import java.util.LinkedList


fun main() {
    println(maxProfit(intArrayOf(2,1,2,0,1)))
}

fun isPalindrome(x: Int): Boolean {
    if (x.toString() == x.toString().reversed())
        return true
    return false
}

fun isPalindromeNew(x: Int): Boolean {
    if(x < 0) return false

    var num = x
    var reversed = 0

    while (num > 0) {
        val lastDigit = getLastDigit(num)

        reversed = addDigitToEnd(reversed, lastDigit)
        num = removeLastDigit(num)
    }

    return reversed == x
}

fun addDigitToEnd(num:Int, digit:Int) = shiftLeft(num) + digit
fun removeLastDigit(num:Int) = num/10

fun getLastDigit(num:Int) = num%10
fun shiftLeft(num:Int) = num*10

fun maxProfit(prices: IntArray): Int {
    var buyPrice = prices.first()
    var sellPrice = 0
    var maxProfit = 0
    for (item in prices) {
        if (item > buyPrice) {
            sellPrice = maxOf(sellPrice, item)
        }
        maxProfit = maxOf(maxProfit, sellPrice - buyPrice)
        if (item < buyPrice) {
            buyPrice = item
            sellPrice = 0
        }
    }
    return maxProfit
}

fun dayi() {
    val lisnked = LinkedList<String>()
}

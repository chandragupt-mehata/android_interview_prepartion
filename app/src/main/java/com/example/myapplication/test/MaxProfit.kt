package com.example.myapplication.test
//8
//4 + 6
fun main() {
    //println(calculateMaxProfit(intArrayOf(7,1,5,3,9,4)))
    println(maxProfit(intArrayOf(7,1,5,3,9,4)))
}

fun calculateMaxProfit(arr: IntArray): Int {
    var buyPrice: Int? = null
    var profit = 0
    for (price in arr) {
        if (buyPrice == null) {
            buyPrice = price
        }
        if (price > buyPrice) {
            profit = maxOf(profit, price - buyPrice)
        }
        if (price < buyPrice) {
            buyPrice = price
        }
    }
    return profit
}

fun maxProfit(prices: IntArray): Int {
    var profit = 0
    for (i in 1 until prices.size){
        if (prices[i] > prices[i - 1]){
            profit += prices[i] - prices[i - 1]
        }
    }
    return profit
}
package com.example.myapplication.test

//7, 1, 5, 3, 6, 4

fun main() {
    println(calculateConsecutiveProfit(intArrayOf(2,1,2,0,1)))
}

fun calculateConsecutiveProfit(input: IntArray): Int {
    var buyPrice = input.first()
    var profit = 0
    var totalProfit = 0
    for (index in 1 until input.size) {
        buyPrice = minOf(buyPrice, input[index])
        if (input[index] > buyPrice) {
            profit = maxOf(profit, input[index] - buyPrice)
        }
        if (input[index] < input[index - 1]) {
            totalProfit += profit
            buyPrice = input[index]
            profit = 0
        }
    }
    if (profit != 0) {
        totalProfit += profit
    }
    return totalProfit
}
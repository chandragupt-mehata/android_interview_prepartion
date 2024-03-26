package com.example.myhiltapplication.test.codingexcercise

/**
 * ServiceNow question - Angular Developer
 *
 * 1. There were no major Angular questions asked
 *
 * 2. Given an array of 0,1 and 2,s, arrange in O(N) time
 *
 * 3. Write a code to explain memorisation
 *
 * 4. How would you insert 1 lakh records in a db of 10 lakh records where there should be no duplication
 *
 * 5. Design Patterns:
 *      * Singleton
 *      * Builder
 *      * Factory
 *      * Abstract Factory
 *      * Chain of Responsibility
 *      * Observable
 *      * Pub Sub
 */
fun main() {
    sortAnArray(intArrayOf(0, 2, 1, 0, 1, 2, 2))
}

fun sortAnArray(input: IntArray) {
    input.sort()
    println(input.toList())
}
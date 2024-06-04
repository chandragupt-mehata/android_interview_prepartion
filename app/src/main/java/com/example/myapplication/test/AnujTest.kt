package com.example.myapplication.test

/**
 * Amex First round
 *
 * What is the sealed class
 * What is sealed interface
 * kotlin coroutine ?
 * Dif thread/coroutine
 * Why coroutine is used
 * Context switching.
 * Synchronisation
 * Coroutines cancellation?
 * some question based on the prev project
 *
 * DS question
 *
 * fun main() {
 *     val hits = """
 *         /home /home/account /home/account/edit_address
 *         /home /home/transactions
 *         /home /home/offers
 *         /home /home/offers /home /home/account
 *         /home /home/transactions /home /home/account /home /home/transactions /home
 *         /home /home/offers /home/transactions /home
 *         /home /home/offers /home/transactions /home /home/offers /home /home/transactions
 *         /home /home/offers
 *         /home /home/transactions
 *         /home /home/transactions /home /home/offers
 *     """.trimIndent()
 *
 * find the count of the each dir.
 *
 * Second Round
 * What is MVVM in details
 * Clean architecture
 * why we use domain layer
 * Test case exp.
 * how to write test case
 * what is your approach
 * What is UI test case and your exp
 * What is espresso and how you write it
 * Dose UI test case run on single thread?
 * Coding
 * You're an android developer, and you've been asked to help our users search
 * their recent transactions. Your product manager tells you that she wants to
 * see the store names autocomplete as the user types so that the user does not
 * need to type the entire store name.
 *
 * You have an api /search?term=$term which returns a list of transactions that match the input term. Allow the user to select a match, if displayed.
 *
 * For example: /search?term=kr might return
 * [
 *   {
 *     "id": "1",
 *     "merchant": "Kroger",
 *     "amount": "$10.00"
 *   },
 *   {
 *     "id": "1",
 *     "merchant": "Kraft",
 *     "amount": "$2.99"
 *   },
 *   {
 *     "id": "1",
 *     "merchant": "K-Mart",
 *     "amount": "$169.33"
 *   }
 * ]
 */

/**
 * [13:21] Anuj Kumar
 * Bharath Gorle
 * Anuj, can you post your answer for coding question
 *
 *     val visitCount = mutableMapOf<String, Int>()
 *
 *     hits.lines().forEach { session ->
 *         session.split(" ").forEach { visit ->
 *             val hierarchy = visit.split("/")
 *             var currentPath = ""
 *             hierarchy.forEach { level ->
 *                 if (level.isNotEmpty()) {
 *                     currentPath += "/$level"
 *                     visitCount[currentPath] = visitCount.getOrDefault(currentPath, 0) + 1
 *                 }
 *             }
 *         }
 *     }
 *
 *     visitCount.forEach { (screen, count) ->
 *         println("$screen: $count")
 *     }
 */

/**
 * val directoryCountMap = mutableMapOf<String, Int>()
 * val data = hits.split('\n').map { it.trim() }
 * data.forEach { row ->
 *     val rowData = row.split(" ").map { it.trim() }.filter { it.isNotEmpty() }
 *     rowData.forEach {
 *         directoryCountMap[it] = directoryCountMap.getOrDefault(it, 0) + 1
 *     }
 * }
 */

val hits = """
    /home /home/account /home/account/edit_address
    /home /home/transactions
    /home /home/offers
    /home /home/offers /home /home/account
    /home /home/transactions /home /home/account /home /home/transactions /home
    /home /home/offers /home/transactions /home
    /home /home/offers /home/transactions /home /home/offers /home /home/transactions
    /home /home/offers
    /home /home/transactions
    /home /home/transactions /home /home/offers
""".trimIndent()

fun main() {
    hits.split("\n")
        .map {
            it.split(" ")
        }
        .flatten()
        .groupBy {
            it
        }
        .map {
            //println(it)
            println("${it.key}  ${it.value.count()}")
        }
}
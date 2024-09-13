package com.example.myapplication.test

import java.util.regex.Pattern

/**
 * You would like to find the sentence containing the largest number of words in some given text. The text is specified as a string S consisting of N characters: letters, spaces, dots (.), question marks (?) and exclamation marks (!).
 *
 * The text can be divided into sentences by splitting it at dots, question marks and exclamation marks. A sentence can be divided into words by splitting it at spaces. A sentence without words is valid, but a valid word must contain at least one letter.
 *
 * For example, given S = "We test coders. Give us a try?", there are three sentences: "We test coders", " Give us a try" and "". The first sentence contains three words: "We", "test" and "coders". The second sentence contains four words: "Give", "us", "a" and "try". The third sentence is empty.
 *
 * Write a function:
 *
 * fun solution(S: String): Int
 *
 * that, given a string S consisting of N characters, returns the maximum number of words in a sentence.
 *
 * For example, given S = "We test coders. Give us a try?", the function should return 4, as explained above.
 *
 * Given S = "Forget  CVs..Save time . x x", the function should return 2, as there are four sentences: "Forget  CVs" (2 words), "" (0 words), "Save time " (2 words) and " x x" (2 words).
 *
 * Assume that:
 *
 * the length of string S is within the range [1..100];
 * string S consists only of letters (a−z, A−Z), spaces, dots (.), question marks (?) and exclamation marks (!).
 */


fun solution(S: String): Int {
    var maxNumberOfWords = 0
    S.split(Pattern.compile("[.\\?]"))
        .filter { it.isNotBlank() }
        .forEach {
            val lengthOfWords = it.split(" ").filter { s -> s.isNotBlank() }.size
            maxNumberOfWords = maxOf(maxNumberOfWords, lengthOfWords)
        }
    return maxNumberOfWords
}

fun main() {
    println(solution("We test coders.    a"))
}

/**
 * You are given a list of all the transactions on a bank account during the year 2020. The account was empty at the beginning of the year (the balance was 0).
 *
 * Each transaction specifies the amount and the date it was executed. If the amount is negative (less than 0) then it was a card payment, otherwise it was an incoming transfer (amount at least 0). The date of each transaction is in YYYY−MM−DD format: for example, 2020−05−20 represents 20th May 2020.
 *
 * Additionally, there is a fee for having a card (omitted in the given transaction list), which is 5 per month. This fee is deducted from the account balance at the end of each month unless there were at least three payments made by card for a total cost of at least 100 within that month.
 *
 * Your task is to compute the final balance of the account at the end of the year 2020.
 *
 * Write a function:
 *
 * fun solution(A: IntArray, D: Array<String>): Int
 *
 * that, given an array A of N integers representing transaction amounts and an array D of N strings representing transaction dates, returns the final balance of the account at the end of the year 2020. Transaction number K (for K within the range [0..N-1]) was executed on the date represented by D[K] for amount A[K].
 *
 * Examples:
 *
 * 1. Given A = [100, 100, 100, −10] and D = ["2020−12−31", "2020−12−22", "2020−12−03", "2020−12−29"], the function should return 230. Total income was equal to 100 + 100 + 100 − 10 = 290 and the fee was paid every month, so 290 - (5 * 12) = 230.
 *
 * 2. Given A = [180, -50, -25, -25] and D = ["2020−01−01", "2020−01−01", "2020−01−01", "2020−01−31"], the function should return 25. The income was equal to 180, the expenditure was equal to 100 and the fee was applied in every month except January: 180 - 100 - (5 * 11) = 25.
 *
 * 3. Given A = [1, -1, 0, -105, 1] and D = ["2020−12−31", "2020−04−04", "2020−04−04", "2020−04−14", "2020−07−12"], the function should return -164. The fee is paid every month. 1 - 1 + 0 - 105 + 1 - (5 * 12) = -164. Note that in April, even though the total cost of card payments was 106 (more than 100), there were only two payments made by card, so the fee was still applied. A transaction of value 0 is considered a positive, incoming transfer.
 *
 * 4. Given A = [100, 100, -10, -20, -30] and D = ["2020−01−01", "2020−02−01", "2020−02−11", "2020−02−05", "2020−02−08"], the function should return 80.
 *
 * 5. Given A = [-60, 60, -40, -20] and D = ["2020−10−01", "2020−02−02", "2020−10−10", "2020−10−30"], the function should return −115.
 *
 * Assume that:
 *
 * N is an integer within the range [1..100];
 * each element of array A is an integer within the range [−1,000..1,000];
 * D contains strings in YYYY−MM−DD format, representing dates in the range 2020−01−01 to 2020−12−31.
 * In your solution, focus on correctness. The performance of your solution will not be the focus of the assessment.
 */
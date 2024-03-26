package com.example.myapplication.test

import java.lang.StringBuilder

fun main() {
    println(output("aaabbaaccccdd", 4))
}

fun output(input: String, repeatation: Int): String {
    var lastChar: Char? = null
    val sb: StringBuilder = StringBuilder()
    var occurance = 0
    for (char in input) {
        if (lastChar == null) {
            lastChar = char
        }
        if (lastChar == char) {
            occurance ++
        } else {
            lastChar = char
            occurance = 1
        }
        if (occurance == repeatation) {
            for (pos in 1..repeatation) {
                sb.append(char)
            }
        }
    }
    return sb.toString()
}
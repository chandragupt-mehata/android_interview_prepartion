package com.example.myapplication.test

import java.lang.StringBuilder

/**
 * String,
 * Boolean,
 * Integer,
 * Byte,
 * Short,
 * Long
 */

fun main() {
    var abc: Boolean = false
    val originalRef = abc
    abc = true

    println("original boolean value: $originalRef")
    println("new boolean value: $abc")
    println("are they same? : ${abc === originalRef}")

    var int1: Int = 12
    val originalInt1 = int1
    int1 = 5
    println("original int value: $originalInt1")
    println("new int value: $int1")
    println("are they same? : ${int1 === originalInt1}")

    var sb1: StringBuilder = StringBuilder()
    val originalSb1 = sb1
    sb1 = StringBuilder()
    sb1.append("what")
    println("original sb value: $originalSb1")
    println("new sb value: $sb1")
    println("are they same? : ${sb1 === originalSb1}")
}
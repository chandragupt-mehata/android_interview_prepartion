package com.example.myapplication.againstswift

fun main() {
    compareWithSwift()
}

/**
 * in swift you must initialize var as in kotlin but you can have let type to declare any identifier only to
 * get rid from compile time error. But it will work like lateinit if we use that variable without initialization, it will throw run time exception.
 */
fun compareWithSwift() {
    var abc: String
    abc = "nil"
    println(abc)
}
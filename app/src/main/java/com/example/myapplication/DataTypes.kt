package com.example.myapplication

fun main() {
    val str = "hello"
    val str1 = "hello"
    val abc = 10
    val mno = 10
    val x = 'c'
    val y = 'c'
    val p1 = Person("hello")
    val p2 = Person("hello")
    println("${str === str1} and ${str == str1}")
    println("${abc === mno} and ${abc == mno}")
    println("${x === y} and ${x == y}")
    println("${p1 == p2}")
}

class Person constructor(val str: String = "") {

}
package com.example.myapplication

fun main() {
    val person: Person1? = null
    doSomething(person ?: Person1())
}

fun doSomething(str: Person1) {

}

class Person1() {

}
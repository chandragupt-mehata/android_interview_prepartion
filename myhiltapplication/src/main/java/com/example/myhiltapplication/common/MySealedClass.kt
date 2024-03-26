package com.example.myhiltapplication.common

/**
 * A sealed class is abstract by itself, it cannot be instantiated directly and can have abstract members
 * A sealed classes is defined a restricted hierarchy.
 * Abstract by default so can not be instantiated.
 * Useful in when expression like enum.
 */
abstract class MySealedClass1 constructor(val abc: String) {

    class Child1: MySealedClass1("hello")
    class Child2: MySealedClass1("Okay")

}

enum class Color constructor(str: String) {
    RED(""), GREEN(""), YELLOW("")
}

fun main() {
    val enumObj1 = Color.GREEN.hashCode()
    val enumObj2 = Color.GREEN.hashCode()

    val sealedObj1 = MySealedClass1.Child1()
    val sealedObj2 = MySealedClass1.Child1()

    println("$enumObj1, $enumObj2 >>>>>> $sealedObj1, $sealedObj2")

    setColor(Color.GREEN)
}

fun setColor(color: Color) {

}
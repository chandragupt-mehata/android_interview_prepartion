package com.example.myapplication.interoperability

class Person {

    fun doSomething(string: String) {
        println("doSomething: $string")
    }
}

fun main() {
    val testJava = TestJava()
    testJava.soIt(null)
    val result: String? = testJava.titleCanBeNull()
    println("result is: $result")

    val output = object : SamInterface {
        override fun firstAbstractMethod() {
            TODO("Not yet implemented")
        }
    }

    val output1 = SamInterface {}

    // way for accessing nested class and inner class. Nested class
    val nestedProperty = OuterClass.NestedClass().abs // nested class object can be accessed using OuterClass in a static way.
    //val innerProperty = OuterClass.InnerClass().innerProperty // compile error
    val innerProperty = OuterClass().InnerClass().innerProperty
}

fun interface SamInterface {
    fun firstAbstractMethod()
    fun nonAbstractMethod() {

    }
}

class OuterClass {

    val outer: String = "outer"

    fun doIt() {
        NestedClass().abs
        InnerClass().innerProperty
    }

    class NestedClass {
        val abs: String = ""

        fun nestedMethod() {
            OuterClass().outer
        }
    }

    inner class InnerClass {
        val innerProperty: String = ""

        fun innerMethod() {
            outer
        }
    }
}

data class JvmFieldDataClass @JvmOverloads constructor(@JvmField val abc: String, val def: String, val defaultValue: String = "notnull")
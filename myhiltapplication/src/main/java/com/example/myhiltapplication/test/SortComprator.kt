package com.example.myhiltapplication.test

fun main() {
    val list = mutableListOf<Student>(Student(10, 2), Student(5, 9))
    val sor = list.sortBy { it.id } // will change original list
    val sor1 = list.sortWith(MyComparator()) // will change original list but it accepts comparator

    val res = list.sortedBy { it.id } // will not change original list
    list.sortedWith(MyComparator()) // will not change original list but it accepts comparator
    println("original list: $list, res: $res")

    val myTest = MyTest("hello")
    myTest.printValue()

    val people = mutableListOf(Person("Alice", 25), Person("Bob", 30), Person("Charlie", 20))
    val sortedPeople = people.sorted()
}

data class Person(val name: String, val age: Int): Comparable<Person> {
    override fun compareTo(other: Person): Int {
        return this.age - other.age
    }
}

class MyComparator: Comparator<Student> {
    override fun compare(p0: Student?, p1: Student?): Int {
        return p0!!.id - p1!!.id
    }
}

class MyComparable: Comparable<Student> {

    override fun compareTo(other: Student): Int {
        TODO("Not yet implemented")
    }

}

data class Student(val id: Int, val rollNo: Int)

sealed class MySealedClass(val str: String){

}

sealed class MySealedClassTwo() {

}

public class TestOne {

}

sealed class ABC {
    class X: ABC(){}
}

enum class Enumnew() {
    a, b, c
}

class MyTest(val str1: String) : MySealedClass(str1) {

    class xz: MySealedClass("")

    fun printValue() {
        println(str1)
    }
}
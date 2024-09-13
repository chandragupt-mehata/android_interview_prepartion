package com.example.myapplication.test

/**
 * Comparable is implemented by the class itself, defining a single natural order, whereas Comparator is a separate object that defines an
 * order for the objects of a class.
 * Comparable is limited to one way of comparison, while Comparator allows you to define multiple ways to compare objects.
 * Comparable is useful when there is a single, natural ordering for a class. Comparator is useful for sorting objects in an order different from the
 * natural ordering or when the class cannot implement Comparable.
 */
fun main() {
    val list = mutableListOf(User(12, "ACB"), User(1, "ABC"), User(10, "CAB"))
    //val res = list.sortedWith(MyComparator())
    //val res = list.sortedBy { it.des }
    val res = list.sortWith(MyComparator())
    println("$res and $list")
}

class MyComparator: Comparator<User> {
    override fun compare(p0: User?, p1: User?): Int {
        return if (p0?.des!! < p1?.des!!) 1 else -1
    }

}

data class User(val id: Int, val des: String)

class ComparableOne(val str: String)

class ComparableTwo(val str2: String): Comparable<ComparableOne> {
    override fun compareTo(other: ComparableOne): Int {
        return if (str2.length > other.str.length) 1 else -1
    }

}
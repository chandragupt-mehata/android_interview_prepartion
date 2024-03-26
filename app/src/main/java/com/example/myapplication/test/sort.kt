package com.example.myapplication.test

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
package com.example.test.dsa
import kotlin.collections.ArrayList

fun main() {
    val list: ArrayList<Int> = ArrayList()
    list.add(10)
    list[0] = 11
    println(list.toString())
    list.add(0, 12)
    println(list.toString())

    val abc = Array(size = 5) {
        0
    }
    //abc[1] = "12" compile error. Because arrays are typed safe. they can not hold other type variable rather than default one
    val abc1 = arrayOf("", 123)
}

/**
 *
 * https://www.udemy.com/course/java-data-structures-and-algorithms-masterclass/learn/lecture/37140112#overview
 *                           time space
 *  creating an array list - O(1) O(n)/O(1) depending upon java version
 *  inserting a value - O(1) O(1) (time complexity is 1 because its simply adding at end)
 *  inserting a value with index - O(n) O(n)/O(1) (time complexity is n because elements need to be shifted after index)
 *  traversing an array - O(n) O(1)
 *  accessing a given cell - O(1) O(1)
 *  searching a given value - O(n) O(1)
 *  deleting a given value - O(n) O(1)
 */
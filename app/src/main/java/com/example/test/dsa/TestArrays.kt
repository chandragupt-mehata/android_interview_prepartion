package com.example.test.dsa

fun main() {
    val arr = IntArray(size = 5, init = {
        Int.MIN_VALUE
    })
    insert(0, 1, arr)
}

fun insert(pos: Int, value: Int, arr: IntArray) {
    try {
        if (arr[pos] == Int.MIN_VALUE) {
            //its empty . add here
            arr[pos] = value
        } else {
            println("cell is already occupied")
        }
    } catch (ex: Exception) {
        println("can not add element to beyond size")
    }
}

/**
 *                      time space
 *  creating an array - O(1) O(n)
 *  inserting a value - O(1) O(1)
 *  traversing an array - O(n) O(1)
 *  accessing a given cell - O(1) O(1)
 *  searching a given value - O(n) O(1)
 *  deleting a given value - O(1) O(1)
 */
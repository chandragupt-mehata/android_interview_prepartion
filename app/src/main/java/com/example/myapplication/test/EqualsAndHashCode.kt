package com.example.myapplication.test

import org.jetbrains.annotations.TestOnly

fun main() {
    val obj1 = E1("abc", "def")
    val obj2 = E1("abc", "def")
    println("hash code: ${obj1.hashCode()} and ${obj2.hashCode()}")

    val obj3 = E2("abc", intArrayOf(1, 2, 3))
    val obj4 = E2("abc", intArrayOf(1, 2, 3))
    println("hash code: ${obj3.hashCode()} and ${obj4.hashCode()} >>> ${obj3 == obj4}")

    val list1 = listOf("abc", "def")
    val list2 = listOf("abc", "def")
    println(list1 == list2)

    val arr1 = intArrayOf(1, 2, 3, 4)
    val arr2 = intArrayOf(1, 2, 3, 4)
    println("${arr1.contentEquals(arr2)} and hash code arr1: ${list1.hashCode()}, arr2: ${list2.hashCode()}") //hash code of multiple instances of list or normal object of data class would be same but not in case of normal class

    val normalClass1 = NormalClass("abc")
    val normalClass2 = NormalClass("abc")
    println(normalClass1.hashCode() == normalClass2.hashCode())

    /*val tempObj = E2("a", intArrayOf(1, 2, 3, 4, 5))
    //equal, hash code contract first comment the hash code impl of E2. then object would be equal because we modified equal method but hash code would be different
    println("hash code: ${obj3.hashCode()} and ${obj4.hashCode()} >>> ${obj3 == obj4}.... tempObj: ${tempObj.hashCode()}")
    // now if we put obj3 in map and try to fetch obj4 from map then it will give null because hash code would be diff (we are using obj3 and obj4 for key)
    val map = HashMap<E2, String>()
    map[obj3] = "hell"
    println(map[tempObj])*/ // not clear

    /**
     * lets change hash code impl like below
     * override fun hashCode(): Int {
     *         var result = str.hashCode()
     *         result = 31 * result + arr.hashCode()
     *         return result
     *     }
     */
    /*println("${obj3.hashCode()} and ${obj4.hashCode()}")
    val map = HashMap<E2, String>()
    map.put(obj3, "hello1") //hash index would be obj3 hash code
    map.put(obj4, "hello2") //hash index would be obj4 hash code and both would be different but since obj3 and obj4 are same hash code should be equal. now
    //since its diff, we will end up with saving hello1 and hello2 in different bucket which is wrong both should be in same bucket and latest one override first one
    println("${map[obj3]} and ${map[obj4]}")*/
}

data class E1(val str: String, val str1: String)

data class E2(val str: String, val arr: IntArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as E2

        if (str != other.str) return false
        if (!arr.contentEquals(other.arr)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = str.hashCode()
        result = 31 * result + arr.contentHashCode()
        return result
    }
}

class NormalClass(val str: String)
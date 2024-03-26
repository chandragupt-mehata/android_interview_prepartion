package com.example.myhiltapplication.test.codingexcercise

import java.lang.StringBuilder
import java.util.Stack

fun main() {
    println(longestCommonPrefixc(arrayOf("abc", "xab", "acx")))
    println(isValidParenthesisN("]"))
}

//abc, abd, abfh, vcgh, edfc
fun longestCommonPrefixDay3(strs: Array<String>): String {
    strs.sortBy { it.length }
    val sb = StringBuilder()
    for (index in 0 until strs[0].length) {
        val targetChar = strs[0][index]
        strs.forEach {
            if (it[index] != targetChar) {
                return sb.toString()
            }
        }
        sb.append(targetChar)
    }
    return sb.toString()
}


fun longestCommonPrefixc(strs: Array<String>): String {
    if (strs.size == 1) {
        return strs.first()
    }
    val strBuilder = StringBuilder()
    strs.sort()
    //println(strs.toList())
    val firstString = strs.first()
    val lastString = strs.last()
    for (index in 0 until firstString.length) {
        if (firstString[index] == lastString[index]) {
            strBuilder.append(firstString[index])
        } else {
            return strBuilder.toString()
        }
    }
    return strBuilder.toString()
}

/**
 * ({[]()})
 */
fun isValidParenthesisN(str: String): Boolean {
    val stack = Stack<String>()
    val map = HashMap<String, String>()
    map[")"] = "("
    map["]"] = "["
    map["}"] = "{"
    str.forEach {
        if (map.containsValue(it.toString())) {
            stack.push(it.toString())
        } else {
            if (stack.isEmpty()) {
                return false
            }
            if (stack.pop() != map[it.toString()]) {
                return false
            }
        }
    }
    return stack.isEmpty()
}

class ListNode(var value: Int) {
         var next: ListNode? = null
}

fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
    if (l1 == null && l2 == null) {
        return null
    }
    if (l1 == null) {
        return l2
    }
    if (l2 == null) {
        return l1
    }
    if (l1.value < l2.value) {
        l1.next = mergeTwoLists(l1.next, l2)
        return l1
    }
    l2.next = mergeTwoLists(l2.next, l1)
    return l2
}

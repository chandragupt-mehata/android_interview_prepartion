package com.example.myhiltapplication.test.codingexcercise

import java.util.Stack

fun main() {
    //println(findDuplicate(intArrayOf(1, 2, 3, 1, 4, 4, 5, 6, 7, 4)).toList())
    //println(longestCommonPrefix(arrayOf("ab", "a")))
    /*val solution = Solution()
    println(solution.lengthOfLongestSubstring2("abcdxbefyz"))*/
    //println(isValidParenthesis("{}[{]"))
    //println(removeDuplicates(intArrayOf(1, 2, 2, 3, 4, 4, 5, 6)))
    //println(strStr("abcde", "cde"))
    println(printDuplicate(intArrayOf(1, 2, 3, 4, 5, 5, 5, 6, 9,7, 8, 7, 8)))
}

// complexicity is not good
fun printDuplicate(input: IntArray) {
    val distinct = input.distinct()
    distinct.forEach {data ->
        if (input.count {
            data == it
            } >= 2) {
            println(data)
        }
    }
}

fun findDuplicate(input: IntArray): IntArray {
    val set = mutableSetOf<Int>()
    val output = mutableSetOf<Int>()
    input.forEach {
        if (set.contains(it)) {
            output.add(it)
        } else {
            set.add(it)
        }
    }
    return output.toIntArray()
}

fun longestCommonPrefix(strs: Array<String>): String {
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

fun isValidParenthesis(s: String): Boolean {
    val stack = Stack<Char>()
    for (char in s) {
        if (char == ')' || char == '}' || char == ']') {
            val top = stack.pop()
            when (char) {
                ')' -> {
                   if (top != '(') {
                       return false
                   }
                }
                '}' -> {
                    if (top != '{') {
                        return false
                    }
                }
                ']' -> {
                    if (top != '[') {
                        return false
                    }
                }
            }
        } else {
            stack.push(char)
        }
    }
    return stack.isEmpty()
}

fun strStr(haystack: String, needle: String): Int {
    if (haystack.contains(needle)) {
        return haystack.indexOf(needle.first())
    }
    return -1
}

fun removeDuplicates(nums: IntArray): Int {
    nums.distinct().let {
        it.forEachIndexed { index, value ->
            nums[index] = value
        }
        return it.size
    }

    var result = 0
    var comparableInt: Int? = null
    for (value in nums) {
        if (comparableInt == null) {
            comparableInt = value
            result ++
        }
        if (comparableInt != value) {
            result ++
            comparableInt = value
        }
    }
    return result
}

class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        if (s == "") {
            return 0
        }
        val arr = arrayListOf<Char>()
        var max = 1
        var k: Int
        for (i in s.indices) {
            if(s[i] !in arr) {
                arr.add(s[i]) // add to array char
                if (arr.size > max) { max = arr.size }
            }
            else {
                k = arr.indexOf(s[i]) // deleting first k symbols
                arr.add(s[i]) // adding for future..
                while (k >= 0) {
                    arr.removeAt(k)
                    k--
                }
            }
        }
        return max
    }

    fun lengthOfLongestSubstring2(s: String): Int {
        if (s == "") {
            return 0
        }
        var result = 0
        val list = mutableListOf<Char>()
        for (char in s.iterator()) {
            if (!list.contains(char)) {
                list.add(char)
                result = maxOf(result, list.size)
            } else {
                var pos = list.indexOf(char)
                while (pos >= 0) {
                    list.removeAt(pos--)
                }
                list.add(char)
            }
        }
        return result
    }
}


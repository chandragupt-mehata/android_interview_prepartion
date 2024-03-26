package com.example.myapplication.test

fun main() {
    println(findNewCharacter("xyz", "xxzyc"))
}

/*
Input: s=xyz and t=xyzc
Output: a
 */
internal fun findNewCharacter(input: String, target: String): Char {
    var input1 = input
    target.forEachIndexed { _, char ->
        if (input1.contains(char)) {
            input1 = input1.replace(char.toString(), "", ignoreCase = true)
        } else {
            return char
        }
    }
    return 'z'
}
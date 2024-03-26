package com.example.myapplication

fun main() {

    val collectionList = listOf("a", "b", "c", "d", "e", "f")
    val newCollectionList = collectionList.map {
        "$it new"
    }
    println("$collectionList \n$newCollectionList")

    val sequenceList = sequenceOf("a", "b", "c", "d", "e", "f")
    val newSequenceList = sequenceList.map {
        println(it)
        "$it new"
    }
    //println("${sequenceList === newSequenceList}")
    newSequenceList.all { it > "a" }
}
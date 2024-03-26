package com.example.myapplication

import java.util.UUID

fun main() {
    val list = mutableListOf("a", "b", "c", "d", "e")
    val newList = mutableListOf(1, 2, 3, 4)
    list.zip(newList).map {
        println("item is: $it")
    }
    val varsc = arrayListOf(13,26,39,42,"String","Raman")
    val varsc1 = arrayOf(13,26,39,42,"String","Raman")

    /*varsc.map { //compile error
        it + 20
    }*/
    val newFlatList = list.flatMap {
        newList
    }
    println("$list \n$newFlatList")

    val str = ""
    val newStr = str.takeIf { it.isNotBlank() }
    println("takeIf: $newStr")


    val result: Result<Int> = event.runCatching {
        value / 0
    }.onFailure {
        println("We failed to divide by zero. Throwable: $it")
    }.onSuccess {
        println("Devision result is $it")
    }
    println("runcatching result: ${result.exceptionOrNull()}")

    val collectionList = listOf("a", "b", "c", "d", "e", "f")
    val newCollectionList = collectionList.map {
        "$it new"
    }
    println("$collectionList \n$newCollectionList")

}

data class Event(
    val id: UUID,
    val value: Int,
    val message: String?,
    var badModifyablePropertyForDemoPurposes: String
)

val event = Event(
    id = UUID.randomUUID(),
    value = 10,
    message = null,
    badModifyablePropertyForDemoPurposes = "Some string"
)



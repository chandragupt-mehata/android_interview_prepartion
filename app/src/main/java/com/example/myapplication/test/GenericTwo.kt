package com.example.myapplication.test

interface AnimalCare<in T> {
    fun takeCareOf(animal: T)
}

open class Animal {

}

class Vet: AnimalCare<Animal> {
    override fun takeCareOf(animal: Animal) {
        TODO("Not yet implemented")
    }

}

fun outFunc(args: AnimalCare<Animal>) {

}

fun testfun(args: AnimalCare<Dog>) {
    //outFunc(args)
}

class Dog: Animal() {

}

class LivingSpecies: Animal() {

}

fun main() {
    //val list: MutableList = mutableListOf("")
    val vet: AnimalCare<Animal> = Vet()
    val child = Dog()
    //outFunc(child)
    //vet.takeCareOf(Dog())

    val list: List<Any> = mutableListOf("3", 123)
    for (item in  list) {
        //item.toInt()
    }
    toIntOnCollectionI(list)

    val boxString = Box("hello")
    val boxInt = Box(123)

    // Checking the type of values stored in the boxes
    if (boxString is Box<String>) {
        println("Box contains a String")
    } else {
        println("Box does not contain a String")
    }

    if (boxInt is Box<Int>) {
        println("Box contains an Int")
    } else {
        println("Box does not contain an Int")
    }

    val result: String = getValue("", "123")
}

fun <T> getValue(t1: T, t2: T): T {
    return t1
}

class Box<T>(val value: T)

fun <T> printType(box: Box<T>) {
    // Attempting to check the type of T at runtime
    if (box is List<*>) {
        println("Box contains a String")
    } else if (box is Box<*>) {
        println("Box contains an Int")
    } else {
        println("Unknown type")
    }
}

fun <T> compareTypes(a: T, b: T): Boolean {
    return a is String && b is String // This line will not work as intended
}

/**
 * When we dont define any upper bound then kotlin add Any? to generic type.
 */
fun <T> toIntOnCollectionI(collection: List<T>) {
    for (item in collection) {
        //item.toInt()
    }
}

fun <T: Number> toIntOnCollection(collection: List<T>) {
    for (item in collection) {
        item.toInt()
    }
}

fun <T> toIntOnCollectionTwo(collection: List<T>) where T: Number{
    for (item in collection) {
        item.toInt()
    }
}

fun <T>String.toChange(abc: T) {

}

class XYZ<T> {

}

class Abc<T: Number> {
    fun abc(t: T) {

    }
}






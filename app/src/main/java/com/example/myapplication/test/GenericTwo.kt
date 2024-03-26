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

    val list = mutableListOf("3", 123)
    for (item in  list) {
        //item.toInt()
    }
}

/**
 * When we dont define nay upper bound then kotlin add Any? to generic type.
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

class Abc<T: Number> {
    fun abc(t: T) {

    }
}






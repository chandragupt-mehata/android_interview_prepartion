package com.example.myapplication.test

fun main() {
    val child = Child()
    child.callIt()

    (1..5).filter { it >3 }.first {
        println("inside collection: $it")
        it > 4
    }

    (1..5).asSequence().filter { it >3 }.first {
        println("inside sequence: $it")
        it > 4
    }
}

open class Parent {
    fun parentMethod() {

    }
}

class ParentTwo: ParentTwoInterface{
    fun parentTwoMethod() {

    }

    override fun parentTwoMethodTwo() {
        println("inside parentTwoMethodTwo")
    }
}

class ParentThree: ParentThreeAbstract {

}

interface ParentThreeAbstract {

}

interface ParentTwoInterface {
    fun parentTwoMethodTwo()
}

class Child: Parent(), ParentTwoInterface by ParentTwo(), ParentThreeAbstract by ParentThree() {
     fun callIt() {
         parentTwoMethodTwo()
     }
}


package com.example.myhiltapplication.common

class TestIt {

    fun testInfixFunction() {
        val p1 = Point(0, 1)
        val p2 = Point(9, 4)
        val z = p1 plus p2
        val isValid = p1.isValid(p1)
        val isValidExtension = p1 isEqual p2
    }

}

class Point(val x: Int, val y: Int) {
    infix operator fun plus(second: Point): Point {
        return Point(second.x + this.x, second.y + this.y)
    }

    infix fun isValid(input: Point): Boolean {
        return true
    }
}

infix fun Point.isEqual(input: Point): Boolean {
    return true
}

class GetterSetter {
    var point: Point = Point(1, 2)
        get() = Point(1, 2)
        set(value) {
            field = value
        }
}

fun main() {
    val getterSetter = GetterSetter()
    getterSetter.point = Point(3, 4)
    println("value is: ${getterSetter.point.x}")
}



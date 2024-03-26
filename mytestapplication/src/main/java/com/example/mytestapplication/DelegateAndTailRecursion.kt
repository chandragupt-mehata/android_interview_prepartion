package com.example.mytestapplication

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

//https://blog.mindorks.com/property-delegation-in-kotlin/
/**
 * A “Delegate” is just a class that provides the value of a property and handles its changes. This
 * will help us to delegate (assign or pass on) , the getter-setter logic altogether to a different class
 * so that it can help us in reusing the code.
 */

var string: String = ""
    set(value) {
        field = "${value.trim()} is a String!"
    }

fun main() {
    callRecursively(0)
    New().callMe()
}

tailrec fun callRecursively(n: Int) {
    if (n != 1000000) {
        var x = n
        callRecursively(++x)
    } else {
        println("done: $n")
    }
}

class New {
    private var trimmedString1 by TrimAppendDelegate()
    private var myIntValue by MyIntegerDelegate()

    fun callMe() {
        trimmedString1 = "hi .......       "
        println(trimmedString1)

        myIntValue = 90
        println("$myIntValue")
    }
}

class TrimAppendDelegate : ReadWriteProperty<Any, String> {
    private var trimAppendedString = ""
    override fun getValue(thisRef: Any, property: KProperty<*>) = trimAppendedString
    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        trimAppendedString = "${value.trim()} is a String!"
    }
}

class MyIntegerDelegate: ReadWriteProperty<Any, Int> {
    private var myInt = 0

    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return myInt
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        myInt = value - 5
    }

}


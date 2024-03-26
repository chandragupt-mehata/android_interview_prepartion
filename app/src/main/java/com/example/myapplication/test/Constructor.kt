package com.example.myapplication.test

fun main() {
    val sample = Sample("t", "u")
    println(sample.z)
}

class Sample(private var s : String) {

    var z = "z"

    init {
        s += "B"
        z += "B"
    }
    constructor(t: String, u: String) : this(t) {
        this.s += u
        this.z += u
    }
}

class MyConstructor constructor (val abs: String) {
    var name: String = ""

    init {
        this.name = ""
    }

    constructor(secondaryValue: String, x: String) : this("") {
        this.name = secondaryValue
    }
}
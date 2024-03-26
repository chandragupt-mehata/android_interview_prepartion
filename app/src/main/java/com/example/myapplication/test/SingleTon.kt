package com.example.myapplication.test


fun main() {
    println("inside main")
    val wrapper = Wrapper()
}

class Wrapper {

    init {
        println("inside Wrapper")
    }

    object SingleTon {
        init {
            println("Singleton init")
        }

        fun callIt() {
            println("Singleton callIt")
        }
    }

    companion object {
        init {
            println("Wrapper companion init")
        }

        fun callIt() {
            println("Wrapper companion callIt")
        }
    }
}

package com.example.myapplication.designpattern

/**
 * https://www.baeldung.com/kotlin/singleton-classes
 */
fun main() {
    Singleton.mockMethod()
    val companionInstance = CompanionSingleton.getInstance("hello")
    companionInstance.mockMethod()

    val enum = SingletonEnum.INSTANCE1
    val enum1 = SingletonEnum.INSTANCE2
    println("enum: ${enum.hashCode()} and enum1: ${enum1.hashCode()}")
    enum.doIt()
}

object Singleton {
    fun mockMethod() {

    }
}

class CompanionSingleton private constructor(private val parameter: String) {

    private val str: String? = null

    fun mockMethod() {
        println("mock companion: $parameter")
    }

    companion object {
        @Volatile
        private var instance: CompanionSingleton? = null

        fun getInstance(abs: String): CompanionSingleton {
            instance ?: synchronized(this) {
                instance = CompanionSingleton(abs)
            }
            return instance!!
        }
    }
}

class LazySingleton private constructor(private val parameter: String) {

    fun mockMethod() {
        println("mock companion: $parameter")
    }

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            LazySingleton("")
        }
    }
}

enum class SingletonEnum {
    INSTANCE1, INSTANCE2;

    fun doIt() {
        println("enum")
    }
}
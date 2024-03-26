package com.example.myapplication.test

/**
 * https://stackoverflow.com/questions/5207115/java-generics-t-vs-object
 *
 * Generics are the powerful features that allow us to define classes, methods and properties which are accessible using different data types
 * while keeping a check on the compile-time type safety. (i.e. check related to data type incompatibilty will be done at compile time only)
 * Creating parameterized classes –
 * A generic type is a class or method that is parameterized over types. We always use angle brackets <> to specify the type parameter in the program.
 *
 * Advantages of generic:
 *
 * Type casting is evitable- No need to typecast the object.
 * Type safety- Generic allows only a single type of object at a time.
 * Compile time safety- Generics code is checked at compile time for the parameterized type so that it avoids run-time error.
 *
 */
fun main() {
    val a = A<String>()
    a.genericFunction("a,b,c")
    a.genericFunctionTwo("a,b,c")
    a.genericFunctionZero(listOf("", 123, "hello"))
    val str = a.process("generic")
    val str1: Any = a.processAny("any")
    if (str1 is String) {
        println("$str1 $str")
    }

    //variance
    val variance = OutVariance<Child>(Child())
    val res: Parent = variance.getValue()

    val inVariance: InVariance<Child> = InVariance(Parent())
}

class A<T> {
    fun genericFunctionZero(obj: List<Any>) {
        println(obj)
    }

    fun genericFunction(obj: Any) {
        if (obj is String) {
            obj.split(",").map {
                println(it)
            }
        }
    }

    fun genericFunctionTwo(obj: T) {
        if (obj is String) {
            obj.split(",").map {
                println(it)
            }
        }
    }

    fun <X> process(variable: X): X {
        return variable
    }

    fun processAny(variable: Any): Any {
        return variable
    }

}

class OutVariance<out T>(private val value: T) {
    fun getValue(): T {
        return value
    }
}

class InVariance<in T>(private val value: T) {
    fun getValue(){
        return
    }
}
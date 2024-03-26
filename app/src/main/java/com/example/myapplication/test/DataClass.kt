package com.example.myapplication.test

/**
 * a data class is a special type of class that is primarily used to hold data. Compiler automatically generate several standard methods,
 * such as toString, hashCode, equals, and copy, based on the properties declared in the class. This can significantly reduce boilerplate code
 * and make your code more concise and readable.
 *
 * However, there are scenarios where using a data class might not be appropriate:
 * Inheritance:
 * Custom Logic:
 * Mutable Properties: Data classes are often used with immutable properties.
 * If you need mutable properties, you might prefer using a regular class with explicit getters and setters.
 *
 * Why data class should be immutable ---
 * Immutable objects, once created, cannot be modified. This property makes the state of an object predictable and helps avoid unexpected changes.
 * With immutable properties, you can be confident that the data represented by a data class instance won't change after it's been created.
 * Thread Safety:
 * Immutable objects are inherently thread-safe. In a multi-threaded environment, if an object's state cannot be changed after creation,
 * there is no need for locks or synchronization mechanisms. This can help prevent race conditions and make concurrent programming more manageable.
 * By keeping properties immutable, data classes provide a straightforward and predictable way to represent and manipulate data in Kotlin.
 *
 * *********
 * Making
 * data classes
 * final ensures consistent behavior across all instances. Users can rely on the fact that two instances of a
 * data class
 * with the same properties will always be equal and behave in a predictable manner
 *
 * ***********
 *
 * In Kotlin, the data class is designed as final to ensure certain properties and behaviors that are important for its intended use as a data holder.
 *
 * Here are some reasons why data classes are final in Kotlin:
 *
 * Immutability: data classes are designed to be immutable by default. Making them final helps enforce immutability, as it prevents the class from being extended and potentially modified in a way that could compromise immutability.
 *
 * Automatic generation of equals(), hashCode(), and toString(): One of the main features of data classes is that the compiler automatically generates equals(), hashCode(), and toString() methods based on the properties declared in the primary constructor. Making the class final ensures that these methods work consistently and predictably for all instances of the class.
 *
 * Copy function: data classes provide a copy() function that allows you to create a new instance of the class with modified properties. Making the class final ensures that the copy() function works as expected and prevents unintended modifications to the class's behavior.
 */
fun main() {
    /*val c = Primary("hello", "extra")
    val c1 = Primary("hello", 10)
    println("${c.extra} and ${c1.extra} and equal: ${c==c1}")

    val person = Person("Sachin")
    val movie = Movie("Border", person)
    val movieCopy = movie.copy() // shallow copy using reference
    println(movie == movieCopy)

    movie.person.name = "Rahul"
    println(movie == movieCopy)
    println(movieCopy)

    val movieCopy2 = movie.copy(person = person.copy())
    println(movieCopy2)
    movie.person.name = "Sachin"
    println(movieCopy2 == movie)*/

    val person1 = Person("hello")
    val person2 = Person("hello")
    println("${person1.hashCode()} and ${person2.hashCode()}")
    println(person1 == person2)

    val normalClass1 = NormalClass1("one")
    normalClass1.field2 = "not cool"
    val copyNormalClass1 = NormalClass1("one")
    copyNormalClass1.field2 = "not cool"
    println(normalClass1 == copyNormalClass1)

    //component
    val (abc, def) = Movie("", Person(""))
}

data class Primary(val name: String, val age: Int) {
    var extra: String = ""
    constructor(name1: String, extra: String): this(name1, 10) {
        this.extra = extra
        println("inside secondary")
    }
}

data class Person(var name: String)

data class Movie(val name: String, val person: Person)

data class PersonIntArray(var name: String, val intArr: IntArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonIntArray

        if (name != other.name) return false
        if (!intArr.contentEquals(other.intArr)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + intArr.contentHashCode()
        return result
    }
}

class NormalClass1(val field1: String) {
    var field2: String = "cool"

    //manual implementation
    override fun equals(other: Any?): Boolean {
        if (other is NormalClass1) {
            return (this.field2 == other.field2) && (this.field1 == other.field1)
        }
        return false
    }

    //manual implementation
    override fun hashCode(): Int {
        var result = field1.hashCode()
        result = 31 * result + field2.hashCode()
        return result
    }
}



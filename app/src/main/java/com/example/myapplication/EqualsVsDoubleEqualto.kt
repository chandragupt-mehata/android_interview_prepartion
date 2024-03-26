package com.example.myapplication

// in kotlin
// https://medium.com/@przemek.materna/kotlin-is-not-primitive-primitives-in-kotlin-and-java-f35713fda5cd
/**
 * Values can be of two types. One of them is a primitive. Number of them is limited: byte, short, int, long, float, double, boolean, char.
 * Those values act on JVM somehow different than reference types. They are put on stack as value. When an object is created it is put in heap. When
 * we pass it somewhere, we do not copy whole object, but we use only address where that object can be found.
 */

/**
 * Kotlin, will convert primitive wrappers to primitive, whenever possible.
 * if its val abc: Int = 10 // it will store abc as primitive
 * if its val abc: Int? = 10 // it will store abc as new instance i.e. Integer using new Integer which can hold null value.
 */

// In Kotlin there are two types of equality:
//
//Structural equality (== - a check for equals()) // as per me it compares hash code since its derived from equals of Any class
//
//Referential equality (=== - two references point to the same object)
fun main() {
    val equals1 = Equals("abc")
    val equals2 = Equals("abc")
    val str = "abc"
    val str1 = "abc"
    val int1 = 10
    val int2 = 10
    println("equals1 == equals2: ${equals1 == equals2}") // false
    println("equals1.abs == equals2.abs: ${equals1.abs == equals2.abs}")
    println("equals1.abs.hashCode() == equals2.abs.hashCode(): ${equals1.abs.hashCode() == equals2.abs.hashCode()}")
    println("equals1.hashCode() == equals2.hashCode(): ${equals1.hashCode()} and ${equals2.hashCode()}")
    println(
        "equals1.identityHashCode() == equals2.identityHashCode(): ${
            System.identityHashCode(
                equals1
            )
        } and ${System.identityHashCode(equals2)}"
    )
    println("equals1 === equals2: ${equals1 === equals2}") // false
    println("str === str1: ${str === str1}")
    println("int1 == int2: ${int1 == int2}")
    println("int1 === int2: ${int1 === int2}")
    println("${int1.hashCode()} and ${int2.hashCode()}") // hash code same because of equals contract
    println("${str.hashCode()} and ${str1.hashCode()}") // hash code same because of equals contract

    val first: Int? = Integer.valueOf(1000)
    val second: Int? = Integer.valueOf(1000)
    println("first === second: ${first === second}") // since both are new instances and they are pointing to diff object
    println("first == second: ${first == second}") // both are having same hash code value
    println("first hash code: ${first.hashCode()} and second hash code: ${second.hashCode()}")
    println("Identity hash code of first: ${System.identityHashCode(first)}")
    println("Identity hash code of second: ${System.identityHashCode(second)}")
    //System.identityHashCode() returns the identity hash code of an object, which is based on the object's memory address or reference.
    //The default implementation of hashCode() in the Object class returns an integer that is derived from the object's memory address. However, it is typically overridden in subclasses to provide a more meaningful hash code based on the object's fields.
    /**
     * first.hashCode() and second.hashCode() both prints values not the address why ?
     * When you call first.hashCode() and second.hashCode(), you're getting hash codes based on the values of the objects themselves, not their memory addresses.
     * This is because the Integer class overrides the hashCode() method to provide a hash code based on the integer value it represents.
     *
     * Any class in kotlin where hashCode will return address not the overridden value ?
     * In Kotlin, there isn't a built-in class where hashCode() returns the memory address of the object directly.
     *
     * The default behavior of hashCode() inherited from Java's Object class doesn't directly return the memory address either;
     * it typically generates a hash code based on some internal mechanism that may or may not be related to memory address. Overriding hashCode() in a
     * class allows developers to customize how hash codes are generated based on the object's state
     */
    /**
     * Returns a hash code value for the object. The general contract of hashCode is:
     *
     * Hash Code: Definition
     * Whenever it is invoked on the same object more than once, the hashCode method must consistently return the same integer, provided no information
     * used in equals comparisons on the object is modified.
     * If two objects are equal according to the equals() method, then calling the hashCode method on each of the two objects must produce the same integer result.
     */


    /**
     * val xyz: Int? = 10
     * val c: Int? = 20
     * Both will be diff object and get stored in heap. Right ?
     *
     * Yes, in the case of val xyz: Int? = 10 and val c: Int? = 20, both variables xyz and c are of type Int?, which is a nullable Int.
     * When you assign integer values to these variables, they are automatically boxed into Integer objects because nullable types in Kotlin are
     * implemented as objects.
     *
     * Therefore, in most cases, especially when dealing with small integer values, the Kotlin runtime may reuse existing objects from a pool for
     * performance reasons. However, this behavior is not guaranteed and might vary depending on the specific conditions and optimizations applied by the JVM.
     *
     * In general terms:
     *
     * 1: If the integer value is within a certain range (commonly -128 to 127), the JVM may use integer caching and reuse objects from the pool.
     * 2: If the integer value is outside that range or if the JVM does not apply such optimizations, new instances may be created for each call to Int?.
     *
     */
    val x: Int? = 150
    val y: Int? = 150

    val xPrimitive: Int = 150
    val yPrimitive: Int = 150
    println(x === y)
    println(xPrimitive === yPrimitive)

    val integerValue: Integer = Integer(42)
    val integerValue2: Integer = Integer(42)
    println("${integerValue === integerValue2}")
}

class Equals constructor(var abs: String)

//https://chat.openai.com/share/89afb8af-c035-4049-9fe0-031bc37d681e
/**
 * but val primitiveInt: Int = 42
 * above one, Int is wrapper class since kotlin does not support direcly int primitive. It meant 42 will be stored as primitive using the wrapper Int.
 * Isn't it some kind of boxing ?
 *
 * **********
 * I apologize for any confusion in my previous responses. Let's clarify:
 *
 * In Kotlin, Int is a non-nullable primitive type, and Int? is a nullable type. There is no direct equivalent of Java's wrapper classes
 * for primitive types in Kotlin. When you declare a variable with the type Int in Kotlin, you're working with a non-nullable primitive type, and there
 * is no boxing involved.
 *
 * So, in the statement val primitiveInt: Int = 42, primitiveInt is an Int variable, and the value 42 is stored directly as a primitive int, not as an
 * Integer object. There is no boxing happening in this case.
 *
 * On the other hand, if you were to use Int? (nullable Int) like this:
 *
 * kotlin
 * Copy code
 * val boxedInt: Int? = 42
 * Then boxedInt is a nullable type, and there would be a form of boxing involved, as the primitive value 42 is automatically wrapped in an Integer object to
 * fit into the nullable Int? type.
 *
 *
 *
 * second question:
 * then what is primitive wrapper in kotlin ?
 *
 * ******
 * In Kotlin, there is no direct concept of primitive wrappers like in some other languages such as Java. In Java, primitive types have corresponding wrapper
 * classes (e.g., int has Integer, double has Double, etc.) that are used for various purposes, including allowing null values (as they are reference types).
 *
 * In Kotlin, the type system is designed to be more concise and unified, and the need for explicit wrapper classes for primitive types is reduced. Kotlin
 * has a concept called "boxed" and "unboxed" types, but this is more of an implementation detail rather than a separate set of wrapper classes.
 *
 * Here's a brief explanation:
 *
 * Boxed Types:
 *
 * In Kotlin, certain types are referred to as "boxed" types when they involve an additional layer of abstraction, typically for nullable types.
 * For example, Int? is a nullable boxed type because it can hold either an Int value or be null. The underlying representation might involve a
 * wrapper object, but this is an implementation detail.
 * Non-nullable types like Int are considered "unboxed" types because they directly represent the primitive values without additional abstraction.
 * Primitive Types:
 *
 * Kotlin itself treats certain types (like Int, Double, etc.) as primitives in terms of language design, and they are optimized for performance.
 * These types are used as efficiently as primitives, and they do not require explicit wrapper classes like in Java.
 * In summary, while Kotlin may use a form of boxing for nullable types behind the scenes, there is no direct concept of primitive wrapper classes for
 * each primitive type in the way it is seen in Java. The type system is designed to be more unified, and nullable types are handled in a way that
 * is more seamless and idiomatic to Kotlin.
 *
 *
 */
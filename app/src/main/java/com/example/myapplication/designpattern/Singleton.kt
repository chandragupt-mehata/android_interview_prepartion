package com.example.myapplication.designpattern

/**
 * https://www.baeldung.com/kotlin/singleton-classes
 *
 * * In Kotlin, while objects are often used for their simplicity and convenience, there are several cases where using an object might not be suitable:
 *  *
 *  * Need for Multiple Instances:
 *  *
 *  * If you need to create multiple instances of a class, using an object (which is a singleton) is not appropriate. Objects in
 *  * Kotlin are singletons by definition, so they are not suitable for scenarios requiring multiple instances.
 *  * kotlin
 *  * Copy code
 *  * class MyClass {
 *  *     // Class definition
 *  * }
 *  *
 *  * val instance1 = MyClass()
 *  * val instance2 = MyClass()
 *  * Inheritance and Polymorphism:
 *  *
 *  * Objects cannot be subclassed, so if you need to create a hierarchy of types or use polymorphism, objects are not the right choice.
 *  * kotlin
 *  * Copy code
 *  * open class ParentClass {
 *  *     // Class definition
 *  * }
 *  *
 *  * class ChildClass : ParentClass() {
 *  *     // Subclass definition
 *  * }
 *  * State Management:
 *  *
 *  * If an object needs to maintain state that is specific to each instance (for example, each user in a multi-user application), using an object is
 *  * not suitable because it would maintain a single, shared state.
 *  * kotlin
 *  * Copy code
 *  * class User(val name: String) {
 *  *     // User-specific state
 *  * }
 *  *
 *  * val user1 = User("Alice")
 *  * val user2 = User("Bob")
 *  * Configuration and Dependency Injection:
 *  *
 *  * When using frameworks or libraries that rely on dependency injection (DI) or require configuration objects, using Kotlin objects might be limiting. DI
 *  * frameworks typically work with class instances rather than singletons.
 *  * kotlin
 *  * Copy code
 *  * class Service(val dependency: Dependency) {
 *  *     // Service definition
 *  * }
 *  *
 *  * val service = Service(dependencyInstance)
 *  * Testing:
 *  *
 *  * Singleton objects can be difficult to test because they maintain state across tests. Using classes allows for easier creation of isolated instances for
 *  * testing purposes.
 *  * kotlin
 *  * Copy code
 *  * class MyService {
 *  *     // Service definition
 *  * }
 *  *
 *  * @Test
 *  * fun testService() {
 *  *     val service = MyService()
 *  *     // Test service instance
 *  * }
 *  * Large and Complex Initializations:
 *  *
 *  * If the initialization of an object involves complex logic or large data structures, it might be better to use a class with an initializer block or a
 *  * factory method to handle this complexity.
 *  * kotlin
 *  * Copy code
 *  * class ComplexClass {
 *  *     init {
 *  *         // Complex initialization logic
 *  *     }
 *  * }
 *  *
 *  * val instance = ComplexClass()
 *  * In summary, while objects in Kotlin are useful for creating singletons and utility classes, they are not suitable for scenarios
 *  * requiring multiple instances, inheritance, instance-specific state, dependency injection, isolated testing, or complex initialization logic.
 *
 *  this is fake i  will complain on consumer form.
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
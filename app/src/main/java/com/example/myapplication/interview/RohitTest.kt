package com.example.myapplication.interview

import java.util.Stack

/*
Design a Credit Card Parser function that will check if a credit card number is valid or not.
Return the name of the issuer if the card is valid. Return null if invalid.
Validity is determined by two values and two criteria.
Value 1: The card number. Example: "3796-1111-2222-1122"
Value 2: The expiration date. Example: "11/22"
Criteria 1: The first four digits identify match an issuer from the following set:
-- ACME: starts with 1121
-- ALFA: starts with 1111
-- AMEX: starts with 3796
Criteria 2: The MM/YY expiration date matches the last four digits.
Example: "3796-1111-2222-1122" and "11/22" is valid. Return "Amex".
*/
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/**
 * Dheeraj
 *
 * amex first round questions
 * Questions on past experience
 * Advantage of MVI over MVVM,
 * explain full process how jetpack compose would interact with viewmodel & repository
 * how to minimise recomposition in jetpack compose
 * how to control situation where state updates trigger recompositions, especially in scenarios with rapid state changes
 * how compose was efficient in your last project
 * how to expose data to compose
 *
 * what is immutability in Kotlin ?
 * Immutability in Kotlin refers to the concept where the state of an object cannot be modified after it has been created. Immutability is important for
 * ensuring that objects remain consistent and predictable, especially in concurrent or parallel programming.
 * In Kotlin, immutability can be achieved through the use of val for read-only properties and immutable data structures. Here are some key aspects of
 * immutability in Kotlin:
 *
 * how immutability affect thread safety
 * how immutability affect concurrency
 * Immutability plays a crucial role in ensuring thread safety. When an object is immutable, its state cannot change after it is created.
 * This has several beneficial implications for concurrent programming:
 *
 * No Need for Synchronization
 * No Race Conditions: Since immutable objects cannot be modified, there is no risk of race conditions where multiple threads attempt to read and
 * write shared data simultaneously.
 *
 * kotlin
 * Copy code
 * val immutableList = listOf(1, 2, 3)
 * // No matter how many threads access this list, it will always be the same.
 * Simplified Concurrency
 * Simplified Concurrency: With immutable objects, you don't need to use complex synchronization mechanisms (like locks or semaphores) to protect shared
 * state, simplifying code and reducing the potential for deadlocks and other concurrency issues.
 *
 * kotlin
 * Copy code
 * class ImmutablePerson(val name: String, val age: Int)
 *
 * val person = ImmutablePerson("Alice", 30)
 * // This instance of ImmutablePerson can be safely shared between threads.
 * Safe Sharing of Data
 * Safe Sharing of Data: Immutable objects can be freely shared between threads without the risk of one thread altering the state in a way that affects
 * other threads. This leads to more predictable and reliable multi-threaded code.
 *
 * how to make data class mutable
 * when to go for mutable class and when for immutable
 * usage of object keyword in Kotlin
 * cases where using object may not be suitable
 */

/**
 * //vZio interview questions
 * Please find the below question asked in  Vizio Interview :
 * Past project experience ?
 * What is MVVM?
 * How we can make network call?
 * In the case of multiple module project what will be the scope of singleton?
 * What is inline, crossline in kotlin?
 * How to check the type of generics at runtime?
 * Why compose is better ?
 * How to call coroutine from compose?
 * What is side effect? How these side effect behave at the time of configuration changes?
 * How to save UI state?
 * In the case of system initiated process death how you can persist the data?
 * Where you used Hot flow and cold flow in your project?
 * Cold Flow: Fetching and displaying the message history when a user opens a chat screen.
 * Each time the screen is opened, the flow will start from the beginning and fetch messages from the database. (considering flow object creation is
 * not being re created on each navigation because if that occurs then in case of hot flow, like shared flow, object would be new for each navigation and
 * flow will be re start for each times.)
 *
 *
 * Difference between Hot Flow and Cold flow?
 * Difference between shared flow and state flow?
 * Why data flow in compose is unidirectional?
 * How you can share the data between two screen ?
 * How view model handle configuration changes and system initiated process death?
 * What is shared view model?
 * How you implement shared view model for two screens?
 * What is live data?
 * What is singleton design pattern?
 * Where you used singleton in your project?
 * Asked follow up questions(scenario based) on the answers.
 * problem solving question
 * /* Given a sorted array I/p  = [-2,-1,0,2,3] output will be
 * o/p = [0,1,4,4,9]
 * with the lowest time complexity */
 */

/**
 * Aakash Kumar
 *
 * EXPEDIA INTERNAL INTERVIEW QUESTIONS:
 * Intro
 * Recent technologies that you have used in your projects?
 * Do you have experience with compose?
 * Do you have experience with GraphQL? we request in form of query to get JSON response from Graph QL service
 * Did you use Scrum or Kanban?
 * Did you have any estimation meetings?
 * What other estimation techniques do you know?
 * Do you know about T-shirt sizing?
 * What version control system did you use?
 * what type of branches did you use?
 * Can you explain the difference between Git Rebase and the Git Merge?
 * What was the process for application to release to the play store?
 * Did you use any release branch?
 * Did you use any CI/CD system?
 * Have you heard about Github Actions? Or did you use it in a personal project?
 * Why is the pull request important and why are we using it?
 * What would you do in the case where you have a pull request and there is a comment which ask you to change something and you disagree with it?
 * What were your main steps when you were reviewing code? how did you achieve it? What did you look for?
 * How did you approach in understanding the pull request and making sure it does what it should do?
 * Did you use KTlint?
 * Can you name a few Code Analyzer Tools?
 * Did you write unit tests?
 * What makes some code harder to test than other?
 * What is dependency injection?
 * What is solid principle?
 * Dependency injection have any relation to testing or to the solid principle?
 * What design patterns do you know?
 * Can you describe me, the observer design pattern?
 * Can you describe the MVVM architecture?
 * Does the MVVM architecture have a reference to the view?
 * What have you used to do multithreading?
 * How do you launch a coroutine?
 * What is the advantage of Coroutines over Threads?
 * How does cancellation work in Coroutines?
 * What is the purpose of the supervisor job?
 * What is the difference between Cold Flow and the hot flow?
 * How can you combine multiple Coroutines?
 * what is the advantage of Kotlin over Java?
 * What does it mean that our variable is immutable?
 * What are scoped functions?
 * What is the difference between on Enum and the Sealed class?
 * What is a data class?
 * What is the context in Android
 * What are the types of Context? In Android, Context is an abstract class that provides access to application-specific resources and classes,
 * as well as up-calls for application-level operations such as launching activities, broadcasting, and receiving intents, etc.
 * Context is a reference to the current state or environment of your app. It’s what your app uses to interact with the Android system.
 * If your app is like a person, then context is like the surroundings or environment that person is in. It helps the person know where they are and
 * what they can interact with.
 *
 * Have you heard about services?
 * Why are we using a broadcast receiver?
 * What is the reason for memory leaks? Objects which are no longer required present in Heap and Garbage Collector unable to remove them because of some reference they hold
 * Do you know any tools you can use to check memory leaks.
 * What is code coverage?
 * Do you think high coverage is enough to say that your application is well tested?
 * Can you explain the difference between a unit test and integration test?
 * Did you write any integration test in the past?
 * In any particular project, did you write any UI test?
 * What's the difference between a view based and declarative framework?
 * What is the role of the modifier?
 * What is the recomposition?
 * how can you reduce the number of recompositions?
 * What is the concept of side effect in jetpack compose?
 * Have you heard about lazy composition?
 * answer: https://medium.com/@husayn.fakher/exploring-the-benefits-of-lazy-columns-in-jetpack-compose-for-smooth-and-efficient-scrolling-9aefc90ebc96
 * What is a composition local? Can you give some useful examples?
 * Can you give some examples of how to optimise jetpack compose code?
 * What is happening when an application is first starting.
 * Application is starting very slowly. What do you suspect is the problem? How would you resolve?
 * What is the android manifest file? Why are you using it?
 * Can you have multiple manifest file?
 * Can you explain deep linking?`
 * Have you heard about AB testing?
 * How do you measure your performance in the application?  what tools can you use?
 * Did you use work manager?
 *
 * chris harman
 */

/**
 * Bharat Expedia Interview
 * EXPEDIA CLIENT FACING ROUND
 *
 * Intros
 *
 * whats your team size?
 *
 * What architectures u have used?
 *
 * What is MVVM?
 *
 * MVVM vs MVI?
 *
 * Unidirectional vs bidirectional data flow
 *
 * What is clean architecture?
 *
 * Have you tried out or worked on anything latest which was recently introduced lets say in 2024
 *
 * Design Task (hackerrank platform):
 *
 * Design a simple weather app (UI not mandatory) to show the response from the API to UI
 *
 * {
 *     "temp": 23,
 *    "unit": "C"
 * }
 *
 *
 * Further questions asked based on my code like placing code to respective packages/layers with clean architecture, why certain classes are in those layers and more
 *
 * Modularizing the layers
 *
 * How the layers are dependent when it is modularized
 */

/**
 * Dhiraj Sharma's Hotstar
 * Questions asked in Hotstar Android Interview
 * In your previous project, how you used to group and manage test cases, explain through example
 * what is suite class used for and how it interacts with yaml file for ci/cd, explain through code
 * what are the steps involved in configuring new test cases in ci/cd pipeline
 * jetpack compose and its advantages
 * challenges faced with jetpack compose in testing
 * Testing custom or complex composables, especially those that involve animations, gestures
 * Write code to create & test a button & textfield in jetpack compose
 * How will you test a recyclerview using espresso
 * Write code to create recyclerview in jetpack compose
 * When a test case fail, how you will debug in ci/cd pipeline
 * When pipeline fail, how will you isolate and run a single unit test
 * How you support old android versions while testing through espresso
 * Write a program for
 *
 * Given two strings A and B, return if they are equal when both are typed into empty text editors. # means a backspace character.
 *            Example :
 *            If  A = "ab#c" and B = "ad#c", then output is True
 *            If  A = "ab#c" and B = "ad#", then output is False
 *
 *       14. In above program, if we cant use deleteCharAt(I used in solution), what will be the alternative approach
 *        15. What other data structures can be used to make above program
 *       16. Write all possible test cases based on below scenario
 *
 * There are two kinds of user- paid and free. Both of them are reaching the hotstar app through deeplinks, however their experience are different, the free user will see an ad widget which when clicked will redirect user to that external app if installed else on playstore. The paid user will not see ad widget at all.
 *
 * Answer - Jetpack compose - Animation,
 * IQVIA - Senior Manager - Anuja Agrawal, Healthcare domain project
 */

/**
 * Saurabh Dhami's
 * ALIB-APPN Android Interview Questions (CI):
 * 1. Entire Android Experience
 * 2. How to use Retrofit in one module and not allow other modules to see it
 * 3. Hide dependency in gradle file to another module
 * 4. How to apply plugin in gradle based on specific flavour
 * 5. Solid Principles
 * 6. Difference between MVVM and MVI
 * 7. Android components
 * 8. How do you communicate and share data between services and activities
 * 9. Lifecycle of a bind service
 * 10. What types of data could be shared between services and activities
 * 11. Limitation of a Bundle
 * 12. How do you ensure an extra layer of encryption while calling an API
 * 13. Difference between symetric and Asymetric Encryption
 * 14. How do you generate symmetric and Asymetric keys
 * 15. How do you get the callback of the button in webpage inside Android app
 * 16. Any Experience on React Native 
 * 17. Why do we need data classes in kotlin
 * 18. Could we inherit one data class from another data class
 * 19. Sealed class and its usage
 * 20. Extension functions in kotlin
 * 21. Could we call extension function in java
 * 22. Agile methodology and its ceremonies
 */

fun main25() {
    val result = CardParser.validate("3796-9999-8888-1216", "12/56")
    println(result)
}

object CardParser {
    fun validate(number: String, date: String): String? {
        return when {
            isValidDate(date = date) -> getCardTypeName(number)
            else -> null
        }
    }

    private val map = mapOf(
        Pair("3796", "AMEX"),
        Pair("1111", "ALFA"),
        Pair("1121", "ACME")
    )

    private val monthDateMap = mapOf(
        Pair(1, 1..31),
        Pair(2, 1..29),
        Pair(3, 1..31),
        Pair(4, 1..30),
        Pair(5, 1..31),
        Pair(6, 1..30),
        Pair(7, 1..31),
        Pair(8, 1..31),
        Pair(9, 1..30),
        Pair(10, 1..31),
        Pair(11, 1..30),
        Pair(12, 1..30),
    )

    private fun getCardTypeName(number: String): String? {
        var cardTypeName: String? = null
        val numList = number.split("-")
        if (numList.size == 4) {
            cardTypeName = map[numList.first()]
        }
        return cardTypeName
    }

    private fun isValidDate(date: String): Boolean {
        val dateList = date.split("/")
        if (dateList.size == 2) {
            val (month, dateV) = dateList.map { it.toIntOrNull() }
            return dateV != null && monthDateMap[month]?.contains(dateV) == true
        }
        return false
    }

}

fun main() {
    /*for (i in 1..1000000) {
        val thread = Thread(Runnable {

        })
        thread.start()
    }*/
    /*println(
        slot(
            arrayOf(
                intArrayOf(1, 3),
                intArrayOf(2, 6),
                intArrayOf(8, 10),
                intArrayOf(15, 18)
            )
        ).toList().forEach {
            println("value is: ${it[0]} and ${it[1]}")
        }
    )*/
    println(validateTwoStrings("#abda#c", "##abda#c"))
}

fun slotT(input: Array<IntArray>): Array<IntArray> {
    var resultArray = arrayListOf<IntArray>()
    /*input.reduce(){
            a,b ->
// if(a[1] < b[0])
        resultArray[0] = intArrayOf(a[0],a[1])
    }*/
    var temp = intArrayOf(Int.MAX_VALUE, Int.MIN_VALUE)
    input.reduce { a, b ->
        if (a[1] < b[0]) {
            temp = intArrayOf(a[0], a[1])
            resultArray.add(temp)
        } else {
            temp = intArrayOf(minOf(a[0], b[0]), maxOf(a[1], b[1]))
            //resultArray.add(intArrayOf(a[0], a[1]))
        }
        b
    }
    return input
}

fun slot(input: Array<IntArray>): Array<IntArray> {
    val result = mutableListOf<IntArray>()
    var start = input[0][0]
    var end = input[0][1]

    for (i in 1 until input.size) {
        val interval = input[i]
        if (interval[0] <= end) {
            // Merge overlapping intervals
            end = maxOf(end, interval[1])
        } else {
            // Add non-overlapping interval to the result
            result.add(intArrayOf(start, end))
            start = interval[0]
            end = interval[1]
        }
    }
    result.add(intArrayOf(start, end))
    return result.toTypedArray()
}

/**
 *  * Given two strings A and B, return if they are equal when both are typed into empty text editors. # means a backspace character.
 *  *            Example :
 *  *            If  A = "ab##c" and B = "ad#c", then output is True
 *  *            If  A = "ab#c" and B = "ad#", then output is False
 */

fun validateTwoStrings(str1: String, str2: String): Boolean {
    val stack = Stack<Char>()
    str1.forEach {
        if (it == '#') {
            if (stack.isNotEmpty()) {
                stack.pop()
            }
        } else {
            stack.push(it)
        }
    }
    val newStr1 = stack.joinToString("")
    stack.clear()
    str2.forEach {
        if (it == '#') {
            if (stack.isNotEmpty()) {
                stack.pop()
            }
        } else {
            stack.push(it)
        }
    }
    val newStr2 = stack.joinToString("")
    println(stack.joinToString{it.toString()} + " and " + newStr2)
    return newStr1 == newStr2
}


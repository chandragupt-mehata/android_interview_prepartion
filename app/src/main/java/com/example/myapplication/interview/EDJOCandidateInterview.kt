package com.example.myapplication.interview

/**
 * CardPayments:
 * You are given a list of all the transactions on a bank account during the year 2020. The account was empty at the beginning of the year (the balance was 0).
 *
 * Each transaction specifies the amount and the date it was executed. If the amount is negative (less than 0) then it was a card payment, otherwise it was an incoming transfer (amount at least 0). The date of each transaction is in YYYY−MM−DD format: for example, 2020−05−20 represents 20th May 2020.
 *
 * Additionally, there is a fee for having a card (omitted in the given transaction list), which is 5 per month. This fee is deducted from the account balance at the end of each month unless there were at least three payments made by card for a total cost of at least 100 within that month.
 *
 * Your task is to compute the final balance of the account at the end of the year 2020.
 *
 * Write a function:
 *
 * fun solution(A: IntArray, D: Array<String>): Int
 *
 * that, given an array A of N integers representing transaction amounts and an array D of N strings representing transaction dates, returns the final balance of the account at the end of the year 2020. Transaction number K (for K within the range [0..N-1]) was executed on the date represented by D[K] for amount A[K].
 *
 * Examples:
 *
 * 1. Given A = [100, 100, 100, −10] and D = ["2020−12−31", "2020−12−22", "2020−12−03", "2020−12−29"], the function should return 230. Total income was equal to 100 + 100 + 100 − 10 = 290 and the fee was paid every month, so 290 - (5 * 12) = 230.
 *
 * 2. Given A = [180, -50, -25, -25] and D = ["2020−01−01", "2020−01−01", "2020−01−01", "2020−01−31"], the function should return 25. The income was equal to 180, the expenditure was equal to 100 and the fee was applied in every month except January: 180 - 100 - (5 * 11) = 25.
 *
 * 3. Given A = [1, -1, 0, -105, 1] and D = ["2020−12−31", "2020−04−04", "2020−04−04", "2020−04−14", "2020−07−12"], the function should return -164. The fee is paid every month. 1 - 1 + 0 - 105 + 1 - (5 * 12) = -164. Note that in April, even though the total cost of card payments was 106 (more than 100), there were only two payments made by card, so the fee was still applied. A transaction of value 0 is considered a positive, incoming transfer.
 *
 * 4. Given A = [100, 100, -10, -20, -30] and D = ["2020−01−01", "2020−02−01", "2020−02−11", "2020−02−05", "2020−02−08"], the function should return 80.
 *
 * 5. Given A = [-60, 60, -40, -20] and D = ["2020−10−01", "2020−02−02", "2020−10−10", "2020−10−30"], the function should return −115.
 *
 * Assume that:
 *
 * N is an integer within the range [1..100];
 * each element of array A is an integer within the range [−1,000..1,000];
 * D contains strings in YYYY−MM−DD format, representing dates in the range 2020−01−01 to 2020−12−31.
 * In your solution, focus on correctness. The performance of your solution will not be the focus of the assessment.
 *
 ******************************************************
 * LongestSentence:
 * You would like to find the sentence containing the largest number of words in some given text. The text is specified as a string S consisting of N characters: letters, spaces, dots (.), question marks (?) and exclamation marks (!).
 *
 * The text can be divided into sentences by splitting it at dots, question marks and exclamation marks. A sentence can be divided into words by splitting it at spaces. A sentence without words is valid, but a valid word must contain at least one letter.
 *
 * For example, given S = "We test coders. Give us a try?", there are three sentences: "We test coders", " Give us a try" and "". The first sentence contains three words: "We", "test" and "coders". The second sentence contains four words: "Give", "us", "a" and "try". The third sentence is empty.
 *
 * Write a function:
 *
 * fun solution(S: String): Int
 *
 * that, given a string S consisting of N characters, returns the maximum number of words in a sentence.
 *
 * For example, given S = "We test coders. Give us a try?", the function should return 4, as explained above.
 *
 * Given S = "Forget  CVs..Save time . x x", the function should return 2, as there are four sentences: "Forget  CVs" (2 words), "" (0 words), "Save time " (2 words) and " x x" (2 words).
 *
 * Assume that:
 *
 * the length of string S is within the range [1..100];
 * string S consists only of letters (a−z, A−Z), spaces, dots (.), question marks (?) and exclamation marks (!).
 * In your solution, focus on correctness. The performance of your solution will not be the focus of the assessment.
 *
 *
 */

/**
 * 1.
 * What is NOT true about ArrayList?*
 * A)
 * It implements List interface
 * B)
 * It has dynamic size
 * C)
 * It's thread safe Correct
 * D)
 * Allows to store same elements
 *
 * 2.
 * Where object exist in memory (JVM runtime)?
 * A)
 * Stack
 * B)
 * Internal storage
 * C)
 * External storage
 * D)
 * Heap Correct
 *
 * 3.
 * What is the difference between launch and async in Kotlin coroutines?
 * A)
 * Async is for sequential funs, while launch for concurrent
 * B)
 * Launch is a part of Android Lifecycle-aware components, while async not
 * C)
 * The difference is that async returns a Job and does not carry any resulting value, while launch returns a Deferred
 * D)
 * The difference is that launch returns a Job and does not carry any resulting value, while async returns a Deferred Correct
 *
 * 4.
 * Which of the following describes the Observer pattern correctly?
 * A)
 * This pattern is used to restore the state of an object to a previous state.
 * B)
 * This pattern is used to reduce communication complexity between multiple objects or classes.
 * C)
 * This pattern is used to get a way to access the elements of a collection object in a sequential manner without any need to know its underlying representation.
 * D)
 * This pattern is used when there is one-to-many relationship between objects such as if one object is modified, its dependent objects are to be notified automatically Correct
 *
 * 6.
 * What is the main idea of using Snapshot in Jetpack Compose?
 * A)
 * Supports advanced state animation capabilities
 * B)
 * Increases performance by reducing the time spent on updating states and rendering
 * C)
 * Allows for automatically tracking state changes, updating the UI as they change, and maintaining state consistency Correct
 * D)
 * Simplifies the process of saving and restoring states between application restarts
 *
 * 7.
 * In Kotlin, which scope function is best suited when you need access to the object's members, and you want to return the result of a lambda expression?
 * A)
 * let Correct
 * B)
 * with
 * C)
 * apply
 * D)
 * also
 *
 * 9.
 * What is the benefit of using @VisibleForTesting annotation?
 * A)
 * to denote that a class, method, or field has its visibility relaxed to make code testable Correct
 * B)
 * to denote that a class, method, or field is visible only in the test code
 * C)
 * to denote that a class, method, or field has its visibility increased to make code less testable
 * D)
 * to throw a run-time error if a class, method, or field with this annotation is accessed improperly
 *
 */

/**
 * 1.
 * Geting element by index from LinkedList takes:
 *
 *
 * A)
 * O(n) Correct
 * B)
 * O(1)
 * C)
 * O(log n)
 * D)
 * O(n log n)
 *
 * 2.
 * What pattern should you choose to traverse a collection and access the collection elements?
 *
 *
 * A)
 * Command
 * B)
 * Interpreter
 * C)
 * Iterator Correct
 * D)
 * Strategy
 *
 * 3.
 * Which methods compiler will generate while creating the data class?
 *
 *
 * A)
 * equals()compareWith()toString()componentN()copy()
 * B)
 * equals()hashCode()toString()componentN()copy()dataMap()dataClear()
 * C)
 * equals()hashCode()toString()componentN()copy() Correct
 * D)
 * toString()componentN()copy()
 *
 * 4.
 * Which SOLID principle is violated in the following code?
 *
 *
 *
 *
 *
 *
 * A)
 * Single Responsibility
 * B)
 * Interface Segregation
 * C)
 * Dependency Inversion
 * D)
 * Liskov Substitution
 *
 * 5.
 * The advantage of using ____ is no state problem because there is only one state for our app, which is a single source of truth
 *
 *
 * A)
 * MVI Correct
 * B)
 * MVVM
 * C)
 * MVP
 * D)
 * MVC
 *
 * 6.
 * What needs to be added to the function name to create a composable function?
 *
 *
 * A)
 * @Preview annotation
 * B)
 * composable keyword
 * C)
 * @Composable annotation Correct
 * D)
 * nothing. All functions can be composable
 *
 * 7.
 * You need to make reading and writing fields in the class thread-safe. What API can be used for this?
 *
 *
 * A)
 * use volatile keyword
 * B)
 * make methods threadsafe
 * C)
 * make methods synchronized Correct
 * D)
 * use singleton
 *
 * 8.
 * What's the proper way to switch from the Main dispatcher to the IO dispatcher in the Kotlin coroutine?
 *
 *
 * A)
 * await(Dispatchers.IO)
 * B)
 * launch(Dispatchers.IO)
 * C)
 * async(Dispatchers.IO)
 * D)
 * withContext(Dispatchers.IO) Correct
 *
 * 9.
 * When should you use the androidTest directory to store your test classes?
 *
 *
 * A)
 * when the tests consist only of unit tests.
 * B)
 * when the tests need to run on your local machine
 * C)
 * when the tests need to run on real or virtual devices Correct
 * D)
 * when the tests visibility has to be relaxed
 */

/**
 * 1.
 * Which interface allows to store key-value pair?
 *
 *
 * A)
 * Map Correct
 * B)
 * Set
 * C)
 * TreeSet
 * D)
 * All of them
 *
 * 2.
 * Hash codes of two objects are the same. Choose the correct assumption
 *
 *
 * A)
 * Objects are equals
 * B)
 * Objects are different
 * C)
 * We have a chance that objects are equals Correct
 * D)
 * Objects have the same link
 *
 * 3.
 * What keyword is used for creating anonymous classes in Kotlin?
 *
 *
 * A)
 * object Correct
 * B)
 * anonymous
 * C)
 * hidden
 * D)
 * new
 *
 * 4.
 * Your code requests data from the local DB only, but when data is requested you update DB from the network and refresh UI with the updated data. What is the principle implemented?
 *
 *
 * A)
 * Single source of truth Correct
 * B)
 * Single Responsibility
 * C)
 * Liskov substitution principle
 * D)
 * Abstraction
 *
 * 5.
 * Which function is used to create the observable state in Jetpack Compose for use with Snapshot?
 *
 *
 * A)
 * mutableStateOf Correct
 * B)
 * createState
 * C)
 * observeState
 * D)
 * mutableLiveData
 *
 * 6.
 * What layer is independent from others according to Clean Architecture?
 *
 *
 * A)
 * Data Layer
 * B)
 * Domain Layer Correct
 * C)
 * Presentation Layer
 * D)
 * All layers have dependencies on each other
 *
 * 7.
 * What dispatcher does ViewModelScope use?
 *
 *
 * A)
 * Dispatchers.IO
 * B)
 * Dispatchers.Main Correct
 * C)
 * Dispatchers.Unconfined
 * D)
 * Dispatchers.Default
 *
 * 8.
 * Which of these patterns is not creational?
 *
 *
 * A)
 * Prototype
 * B)
 * Facade Correct
 * C)
 * Factory
 * D)
 * Builder
 *
 * 9.
 * Which coroutine builder is used for testing?
 *
 *
 * A)
 * launch
 * B)
 * async
 * C)
 * runBlocking Correct
 * D)
 * await
 */

class EDJOCandidateInterview {
}
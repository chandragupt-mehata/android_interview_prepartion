package com.example.myapplication.interview

/**
 * turn on video.
 * how are you? Good morning if I am not wrong its morning at your place
 * recording for future reference.
 * turn on video.
 *
 *
 * Self intro - I am senior android engineer here at EPAM, I am having more than 12 years of exp in mobile development mostly worked on andorid.
 * but having couple of years of experience in cross platform which is on flutter also. I am here to know more about u and technical skills which
 * is needed for current position..
 * Why don't you introduce yourself which will include your role in your recent prjct.
 *
 * Let me give you a brief idea about today’s discussion.
 *  * recording for future reference.
 *
 * We will start with some general concept and principle which involved in developing any software -
 * Then will cover kotlin basic followed up with android basics,
 * If we will have time then we will discuss about best practices to improve the app performance. considering last 25-30 minutes would be there for coding exercise.
 *
 *  * recording for future reference.
 *  ***** recording / turn on video ************
 *
 * 1. Could you please explain SOLID principle. (Just full form and why is it needed. No need to go into details for each one)
 * 2. You need to add a new feature to your app that involves sending a notification in a different way or using diff component. How would
 * you design your code to adhere to the Open/Closed Principle, ensuring that your existing code remains unchanged?
 *
 * Ans:
 * interface Notification {
 *     fun sendNotification()
 * }
 *
 * class PushNotification : Notification {
 *     override fun sendNotification() {
 *         // send push notification
 *     }
 * }
 *
 * class EmailNotification : Notification
 *
 * class NotificationServiceNew : Notification
 *
 * 3.In Android development, when would you use the Singleton pattern, and what potential issues should you be aware of when implementing it?"
 * Ans: The Singleton pattern is useful for creating a single, globally accessible instance of a class, such as
 * a DatabaseManager or ConfigurationManager. However, be cautious of potential issues such as making it difficult to unit test, introducing global state
 * that can lead to unexpected behavior, and potential issues with multithreading if the instance creation is not handled properly. It’s essential to ensure
 * thread-safety when implementing a Singleton.
 *
 * 4. "How would you use the Builder pattern to construct a complex UserProfile object in your Android app? What benefits does this pattern provide in this context?"
 * Ans:The Builder pattern allows for constructing complex objects step by step, which is useful for creating a UserProfile with many optional parameters. Create
 * a UserProfileBuilder class with methods for setting each property (e.g., setName(), setEmail(), etc.) and a build() method that assembles the UserProfile instance.
 *
 * 5. "How can a poorly implemented design pattern lead to memory leaks in an Android application? Give an example."
 * Ans: A poorly implemented Singleton pattern, for example, can lead to memory leaks if the Singleton instance holds references to Context objects or
 * other large objects that prevent them from being garbage collected. For instance, if a Singleton holds a reference to an Activity or View, it
 * can prevent these objects from being collected when they are no longer needed, causing memory leaks. Ensure that Singleton instances do not hold
 * references to Context or other objects that have a lifecycle.
 *
 * ************************
 * 1. What is data class in kotlin and why do we need that?
 * 2. "What happens if you override equals() in a data class but do not override hashCode()? What issues might arise?
 * Answer: If you override equals() in a data class but do not override hashCode(), you may encounter issues with collections that rely on hash codes,
 * such as HashSet or HashMap. The contract between equals() and hashCode() stipulates that if two objects are considered equal by the
 * equals() method, they must have the same hash code. Failing to override hashCode() when equals() is overridden can lead to inconsistent behavior where
 * equal objects are not treated as equal in hash-based collections, causing potential bugs and unexpected behavior.
val testClass1 = TestClass("hello")
val testClass2 = TestClass("hello")
println("are both objects equals: ${testClass1 == testClass2}")

class TestClass(val str: String)
data class TestClass(val str: String)

 * 3. What is a sealed class in Kotlin, and how does it differ from a regular class or an abstract class?
 *
 * 4. "How do you use a sealed class to implement a state machine, and why would it be advantageous compared to using a simple enum class?"
 * Answer: A sealed class is ideal for modeling a state machine because it allows you to represent various states and transitions between them with more flexibility and type safety
 * than enums. Each subclass of the sealed class can carry additional data or behavior relevant to its specific state.
 *
 * 5. What is lambda function and what's benifit of using it?
 * 6. What is HOF? Create a higher-order function that accepts a function as a parameter. This parameter function will square
 * the input integer. The higher-order function should apply this squaring function to an integer input and return the result to the caller.


 * 7. Inline function?
 * 8. Can object be inherited?
 * 9. Diff between val and const?
 * 10. lazy vs val?
 * 11. Object vs companion object?
 * 12. Structured equality vs referencial equality. double equal to vs tripple equal to
class TestClass(val str: String)
val testClass1 = TestClass("hello")
val testClass2 = TestClass("hello")
println("are both objects equals: ${testClass1 == testClass2}")

val value1: Int = 12
val value2: Int = 12
println("are both primitive equals: ${value1 == value2}")
 *
 *
 * *****
 * Coroutines:
 * 1. What is coroutine and how is it different from Thread? Infinite loop inside a coroutine, will it block the thread?
 * 2. What is suspend function? How is it diff from regular function?
 * 3. Does suspend function not block underlying thread?
 * Example:
coroutineScope {
launch {
while (true) {
println("hello inside first coroutine")
}
}
launch {
for (item in 1..10) {
delay(100)
println("hello inside second coroutine")
}
}
}
 * 4. Does a suspend function execute on a different thread by default?
 * 5. "Can you use suspend functions in combination with callback-based APIs? How do you handle this?"
 * suspend fun fetchData(): String = suspendCoroutine { continuation ->
 *     // Simulate an asynchronous operation with a callback
 *     someAsyncApiCall(object : Callback {
 *         override fun onSuccess(data: String) {
 *             continuation.resume(data)
 *         }
 *
 *         override fun onFailure(error: Throwable) {
 *             continuation.resumeWithException(error)
 *         }
 *     })
 * }
 * 6. What is dispatcher? Diff between default and io dispatcher. Diff MAin vs mAin.immediate
 * 7. How does cancellation work in coroutine? What if we cancel the parent scope.
 * 8. What is structured concurrency? Parent wont be completed until all it's children
 * 9. Exception handling in coroutine?
 * 10. Flow vs Channel?
 * 11. State flow vs Shared Flow?
 * 12. Backpressure handling? What happens if a collector is too slow in collecting values from a SharedFlow? How would you handle this scenario?
 * 13. Operators in flow?
 * 14. Diff between live data and state object?
 * 15. flowOn operator? or How will you delegate the emitter task to IO thread? On which thread collector will run?
 * 16. You have a Flow that emits values continuously. You want to share this Flow across multiple collectors so that each collector receives
 * the same emitted values. How would you achieve this?
 * 17. two tasks are suppose to run one by one in sequence order. Both task are under try catch block to handle all exception.
 * While one task was ongoing user presses back button now as it will cancel the coroutine scope
 * considering scope is tied up with lifecycle scope. What would be behaviour? Now as exception will get caught up in first task it will allow second task to be continued which wont be an
 * expected behaviour.
 *
 *
 * State vs. Event Semantics:
 * Problem: StateFlow is designed for representing ongoing state rather than one-time events. It continuously holds the latest value, which is intended
 * to represent a state rather than a discrete event.
 * Example: If you use StateFlow to represent navigation commands, it would always hold the latest command, and if a new command is
 * issued before the previous one is handled, the previous command might be overwritten.
 * 17. What happens if a Flow emits a value after the coroutine that collects it has been canceled?
 * When a Flow emits a value after the coroutine that collects it has been canceled, the emitted value is generally ignored, and the emission does
 * not reach the canceled collector. When the coroutine collecting the Flow is canceled (e.g., when the lifecycle moves out of the active state in Android,
 * such as from STARTED to STOPPED), the Flow's collection is immediately stopped. Any ongoing operations related to the collection (such
 * as suspending functions or downstream operators) will be canceled as well.
 * 18. What are the potential performance issues when using Flows in a UI-heavy application, and how would you mitigate them?
 * Excessive Recomputations:Flows that emit frequently or are collected in multiple places can lead to excessive recomputations or updates in the
 * UI, causing performance degradation.
 * Solution: Use operators like debounce or throttleFirst to reduce the frequency of emissions and avoid excessive UI updates.
 * Ensure that Flows are collected in a lifecycle-aware manner using tools like repeatOnLifecycle or viewLifecycleOwner.lifecycleScope to prevent memory leaks.
 *
 * ********************************************
 * 1. Explain the differences between Thread, Executor, and Coroutine in Android. When would you use each?
 * Thread is a low-level construct for creating and managing threads manually. Executor provides a higher-level abstraction for managing thread pools and
 * task execution. Coroutine is a Kotlin feature for asynchronous programming, offering a more flexible and less error-prone way
 * to manage concurrency compared to traditional threading.
 * 2. Concurrent vs Paralalism
 * Concurrency refers to the ability of a system to handle multiple tasks or processes at the same time. It involves managing multiple tasks
 * that may be executed in overlapping time periods but not necessarily simultaneously. Concurrency is about dealing with lots of things at once, even if
 * they are not executing at the same time.
 * 3. Describe how you would use synchronized, ReentrantLock, and Semaphore in Java to handle synchronization. When might you choose one over the others?
 * synchronized is a Java keyword for locking access to a block of code. ReentrantLock is a more advanced lock with features like try-lock
 * and timed lock. Semaphore controls access to a resource through counting. The choice depends on the requirements for lock behavior and resource management.
 * 4. Atomic vs volatile?
 * 5. What is WorkManager, and what are its primary use cases? How does it differ from JobScheduler and AlarmManager?
 * 6. How does WorkManager ensure that tasks are executed even if the app is terminated or the device restarts?
 * Answer: WorkManager uses a combination of JobScheduler, AlarmManager, and its own internal mechanisms to ensure tasks are executed.
 * It maintains a persistent job queue and uses constraints to schedule work. The WorkManager library guarantees that tasks will be executed based on
 * constraints such as network availability or battery status, and it handles retries and rescheduling if necessary, even across device restarts
 * or app terminations.
 * 7. What are the key considerations for creating a foreground service, and how does it differ from a background service?
 * 8. What are the latest changes introduced by Android? Behaviour changes for all apps and target specific
 * 9. Internal working of hash map?
 * 10. AAB vs APK?
 * 11. Best security practices while developing an app? Where should we store sensitive info? Jetpack security? How does encrypted shared preference work?
 * 12. Proguard? What kind of data you dont want to obfuscate?
 * 13. serialization vs parcellable?
 * 14. Thread vs process? How do you communicate between two process?
 * 15. Memory leak? Stack vs heap? Tools? GC? How do you identify if there is any memory leakage? Any tool have you used in past?
 *
 * Dagger vs Hilt?
 * scope - tells same or diff instances will get created on each @Inject
 * Roles of component (How long that component will live that depends upon type of component)
 *  * Injection Targets:
 *  * Components define where dependencies can be injected. For example, dependencies provided by ApplicationComponent can be injected anywhere
 * Can we inject @ActivityScoped dependency inside viewModel? No
 * Purpose of @EntryPoint:
 * @EntryPoint is used to define a way to retrieve dependencies from the Hilt container in cases where you can't use constructor injection or field injection, such as in third-party libraries or when injecting into a class outside the Android component tree (e.g., BroadcastReceiver).
 *
 * MVVM vs MVI? What is clean architecture? Domain layer usage?
 * Can we pass context to view model? What would be drawback?
 *
 *
 * 16. Firebase A/B Testings? FirebaseRemoteConfig?
 *
 * 17. Which build tool have you used for development? Gradle vs Maven? api vs implementation? what is compileTimeOnly vs RuntimeOnly dependency?
 *
 * 18. Have you worked on any git, SVN ? Git rebase vs git merge // git pull vs git fetch
 *
 * 19. CI/CD ?
 *
 * *************************************
 * Jetpack compose
 * 1. What are the key differences between Jetpack Compose and the traditional Android View system?
 * Purpose:
 * To assess their understanding of the fundamental shift in UI development with Jetpack Compose compared to the XML-based View system.
 * Look for:
 * Differences in UI rendering, declarative vs. imperative paradigms, state management, reusability, and how Compose simplifies complex UI interactions.
 * 2. Can you explain how the recomposition mechanism works in Jetpack Compose? How do you optimize performance in Compose?
 * Purpose:
 * To assess their understanding of the internal workings of Jetpack Compose, particularly around performance optimizations.
 * Look for:
 * Explanation of what triggers recompositions, how to minimize recompositions using tools like remember and key, and best practices for building efficient UIs in Compose.
 * When the content of a list changes (e.g., items are added, removed, or reordered), key allows Compose to track which items have changed, ensuring that only the necessary items are recomposed.
 * @Composable
 * fun ItemList(items: List<String>) {
 *     Column {
 *         items.forEach { item ->
 *             key(item) {  // Using 'key' to uniquely identify each item
 *                 Text(text = item)
 *             }
 *         }
 *     }
 * }
 * 3. What are some of the challenges you've faced when using Jetpack Compose, and how did you overcome them?
 * 4. How would you approach integrating Jetpack Compose into an existing Android project that primarily uses XML-based layouts?
 * 5. How do you test composables in Jetpack Compose? What tools and techniques do you use?
 * Experience with ComposeTestRule, creating and testing @Composable functions, managing test state, and using tools like Espresso for Compose.
 *
 * *********************************************************
 * Unit Test
 * 1. What are the different types of test cases you write in an Android project, and when do you use each type?
 * Unit Tests: Fast, isolated tests that validate individual components, functions, or methods (e.g., using JUnit).
 * Integration Tests: Tests that validate the interaction between multiple components (e.g., testing ViewModel logic with LiveData or Flow).
 * UI Tests: Tests that validate the app's UI and user interactions (e.g., using Espresso or Compose testing).
 * 2.
 */

/**
 * EDJO: This app provides services to financial advisors (FAs) and branch office administrators (BOAs). It
 * integrates client management, appointment scheduling, and document handling functionalities into a unified solution. FAs can efficiently access
 * client account information, monitor activity, schedule appointments, and securely manage and retrieve financial documents, all within a user-centric
 * interface. This app supports Edward Jones professionals in delivering superior client service and optimising operational efficiency.
 * Calendar - for android
 * Holdings, Scanner - For iOS
 * Canada Online Access App - Legacy one
 */

class InterviewQuestionsToBeAsked {

    val abc by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        ""
    }
}
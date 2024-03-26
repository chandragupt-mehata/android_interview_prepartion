package com.example.myapplication

/**
 * Thread pools, thread executors, thread scheduling, and handlers are all related to managing concurrency
 * and parallelism in Java and Android development, but they serve different purposes and work at different
 * levels of abstraction.
 *
 * Thread Pools: A thread pool is a collection of worker threads that are available for executing tasks. Instead of creating a new thread for every task,
 * which can be inefficient due to the overhead of thread creation and destruction, a thread pool reuses a fixed number of threads to execute multiple tasks.
 * Thread pools help in managing and controlling the number of active threads, which can lead to better resource utilization and improved performance. In Java,
 * you can create thread pools using classes like ThreadPoolExecutor or the utility class Executors.
 *
 * Thread Executors: A thread executor is an abstraction that provides a high-level API for managing and executing tasks asynchronously using thread pools. It
 * encapsulates the details of thread creation, management, and scheduling, allowing you to focus on the tasks to be executed. Thread executors
 * typically provide methods for submitting tasks for execution and controlling the lifecycle of the executor. They simplify
 * concurrent programming by abstracting away low-level threading details.
 *
 * Thread Scheduling: Thread scheduling refers to the process of determining which thread should run next on the CPU. In a multi-threaded
 * environment, multiple threads may compete for CPU resources, and the operating system's scheduler decides the order in which threads are executed.
 * Thread scheduling policies vary across operating systems and may depend on factors such as thread priority, time slicing, and scheduling algorithms.
 *
 * Handler: In the context of Android development, a Handler is a mechanism for scheduling and processing messages or "runnables" on a thread's message queue.
 * Handlers are often used to communicate between worker threads and the main (UI) thread in Android applications. They allow you to schedule
 * tasks to be executed asynchronously on a specific thread, typically the main thread, which is necessary for updating the user interface safely.
 *
 * In summary, thread pools and executors provide a way to manage and execute tasks concurrently using multiple threads efficiently. Thread scheduling is
 * handled by the operating system's scheduler, which determines the order of thread execution. Handlers, on the other hand, facilitate communication and
 * task scheduling within an application, particularly in the context of Android development.
 */

// question: Lets say I am having 100 task which I want to execute via a thread pool having a capacity of 10 threads. How to write a proper code in kotlin
// answer: verifyFirst()
import java.util.concurrent.Callable
import java.util.concurrent.Executors

fun main() {
    //verifyFirst()
    verifyFutureWait()
}

fun verifyFutureWait() {
    // Number of tasks
    val numTasks = 100

    // Thread pool size
    val poolSize = 10

    // Create a thread pool with a fixed number of threads
    val executor = Executors.newFixedThreadPool(poolSize)

    // Submit tasks to the executor
    println("hii")
    val result = executor.submit(TaskRunnableFutureWait(25))
    println("end of result: ${result.get()}") // get function is blocking call which will hold the control until it gets result but that wont block that thread,

    // Shutdown the executor when all tasks are completed
    executor.shutdown()
}

fun verifyFirst() {
    // Number of tasks
    val numTasks = 100

    // Thread pool size
    val poolSize = 10

    // Create a thread pool with a fixed number of threads
    val executor = Executors.newFixedThreadPool(poolSize)

    // Submit tasks to the executor
    println("hii")
    repeat(numTasks) {
        executor.submit(TaskRunnable(it + 1))
    }
    println("end of result")

    // Shutdown the executor when all tasks are completed
    executor.shutdown()
}

// Define a Runnable task
class TaskRunnable(private val taskId: Int) : Runnable {
    override fun run() {
        // Perform task execution here
        Thread.sleep(5000)
        println("Task $taskId executed by thread ${Thread.currentThread().name}")
    }
}

class TaskRunnableFutureWait(private val taskId: Int) : Callable<String> {
    override fun call(): String {
        // Perform task execution here
        Thread.sleep(10000)
        println("Task $taskId executed by thread ${Thread.currentThread().name}")
        return "callable"
    }
}

package com.example.myaidlserver

/**
 * https://www.youtube.com/watch?v=4rLW7zg21gI
 * https://app.pluralsight.com/ilx/video-courses/1df9b18d-34cc-4061-94be-c1ab36113db4/7bad0db5-36a3-467d-8dc8-0312ee72b465/30f97651-3649-428a-8b3f-bf4d47c3bdab
 *
 * What is Process?
 * A process is an instance of a program that is being executed. When we run a program, it does not execute directly.
 * It takes some time to follow all the steps required to execute the program, and following these execution steps is known as a process.
 * In short - An independent unit of execution containing its own memory space.
 * Has its own separate memory space.
 * Higher overhead due to separate memory and resource allocation.
 * Operates independently.
 * Communicates through IPC mechanisms.
 * Each process has its own resources (files, variables, stack, heap, pointer, register etc.).
 * One process can not corrupt other process memory space as each will have their own memory.Ex: Google chrome -
 * which run each tab in diff process.
 *
 * A process can create other processes to perform multiple tasks at a time; the created processes are known as clone or
 * child process, and the main process is known as the parent process. Each process contains its own memory space and does not share
 * it with the other processes. It is known as the active entity. A typical process remains in the below form in memory.
 *
 * Android is based on linux so “the Android system starts a new Linux process for the application with
 * a single thread of execution. By default, all components of the same application run in the same process and thread (called the “main” thread).”
 *
 * A thread is the subset of a process and is also known as the lightweight process. A process can have more than one thread,
 * and these threads are managed independently by the scheduler. All the threads within one process are interrelated to each other.
 * In short - A lightweight, executable unit within a process.
 * Shares memory space with other threads within the same process.
 * Lower overhead, more efficient in resource usage.
 * Depends on the process; multiple threads can run in parallel within one process.
 * Communicates directly through shared memory.
 * Share resources of the process they belong to.
 *
 * *****
 * Thread wrt stack
 * What about thread? How does they access memory of process wrt stack, heap etc?
 *
 * ChatGPT
 * Threads within the same process share the same heap but have their own stacks. Here's how it works:
 *
 * Heap: All threads in a process share the same heap memory. This means they can access and modify the same objects and data stored in the heap. This
 * shared access necessitates synchronization mechanisms (like synchronized blocks or locks) to prevent concurrent modification issues.
 *
 * Stack: Each thread has its own stack. The stack stores the thread's local variables, method call information, and control flow. Since each thread
 * has its own stack, local variables are not shared between threads.
 *
 */
class ServiceInterProcessCommunication {
}
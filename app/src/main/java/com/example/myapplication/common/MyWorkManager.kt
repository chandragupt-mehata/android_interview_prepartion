package com.example.myapplication.common

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

/**
 * Starting from Android O (API level 26), the system imposes restrictions on background services to improve battery life and overall performance. This makes choosing the right background processing component even more crucial. Here’s how the updated guidelines fit into the context of Android O and beyond:
 *
 * 1. Thread
 * Threads can still be used for short-lived, immediate tasks, but you need to ensure that they are properly managed to avoid leaks and resource exhaustion.
 *
 * 2. Service
 * Foreground Services: Use Foreground Service if the task must continue running even when the app is not in the foreground. This type of service shows a notification to the user to indicate that it's running, and thus the system is less likely to kill it.
 *
 * Use Case: Playing music, tracking location.
 * Background Services: For non-foreground tasks, you should generally avoid using Service because of restrictions. Instead, consider using JobIntentService or WorkManager.
 *
 * 3. JobIntentService
 * When to Use: Use JobIntentService as a replacement for IntentService if you need background execution that respects the system’s restrictions on background services.
 *
 * Use Case: Handling asynchronous tasks that need to be completed but can be deferred and don’t need immediate execution.
 * Benefits: It queues work and executes it using the JobScheduler when the system is ready, ensuring compliance with the background execution limits.
 *
 * 4. WorkManager
 * When to Use: WorkManager is highly recommended for background tasks that need guaranteed execution, respecting system constraints and lifecycle changes.
 *
 * Use Case: Periodic data syncing, deferred background processing, tasks with constraints.
 * Benefits:
 *
 * Guaranteed Execution: Ensures tasks are executed even if the app is killed or the device restarts.
 * Constraint Management: Handles network constraints, battery status, etc., automatically.
 * Compatibility: Automatically uses the best available scheduler based on the API level (e.g., JobScheduler on newer APIs, AlarmManager and BroadcastReceiver on older ones).
 * Summary Updated for Android O and Beyond
 * Thread: Still suitable for short-lived tasks, but manage lifecycle carefully to avoid leaks.
 * Foreground Service: Use for long-running tasks that need to show a notification and run continuously.
 * JobIntentService: Use for tasks that need background execution but are not suitable for Foreground Service. It ensures compatibility with the background execution limits of Android O and beyond.
 * WorkManager: Preferred for most background tasks that require guaranteed execution, constraint handling, and compatibility with various API levels.
 *
 *
 *
 *
 * The Work Manager is a service provided by the Android operating system. It is part of the Android Architecture Components included with
 * Android Jetpack. The purpose of the Work Manager is to provide a consistent API for scheduling and managing background tasks that
 * is backward‑compatible with earlier versions of the operating system.
 *
 * The Android operating system has been around for a number of years, and over the different versions, there have been a number of mechanisms to
 * run background tasks. But why run tasks in the background? The obvious answer is to avoid doing too much work on the main thread. Also called the
 * UI thread, the main thread handles interaction with the user interface. If too much work is done on the UI thread, the application can appear
 * sluggish or, worse, frozen. But WorkManager goes beyond the user experience. WorkManager is kind of like Federal Express. When your work absolutely
 * positively has to get done, you call on the WorkManager. Tasks handled by the WorkManager are completed even if the application that started them is
 * unloaded. In fact, work will still complete even if the device reboots. One of the first attempts at scheduling background tasks is called the
 * AlarmManager. The AlarmManager schedules work to repeat at specified intervals. When using the AlarmManager, you have to be careful when designing
 * alarms. For example, the AlarmManager can wake up the device and perform batch tasks even if the device is low on battery. This could completely exhaust
 * the remaining charge and the device would power off. There are a number of gotchas the documentation mentions to avoid when using the AlarmManager.
 * There is also the AsyncTask, but the API for AsyncTask comes with a steep learning curve. The JobScheduler is also available, and the JobScheduler
 * fixes some of the issues of the AlarmManager in AsyncTask, and it adds some new features. Unfortunately, backward compatibility is not one of them.
 * It only works some more recent versions. The moral of the story is that before WorkManager to support background tasks on a wide range of Android devices,
 * your code had to support multiple APIs, so you had to design, write, test, and maintain multiple code bases to achieve the same result. This violates a
 * fundamental principle of software development, DRY, or don't repeat yourself. The WorkManager was introduced to solve these problems. As mentioned,
 * one benefit of the WorkManager is that it offers an API that is consistent and backward compatible across different versions of the Android
 * operating system. For devices running API level 23 or higher, WorkManager will defer to the JobScheduler. For earlier versions, as far back as AP1
 * level 14, it can leverage the AlarmManager. But the best part is you don't have to manage or even worry about this. WorkManager abstracts the complexity.
 * It's like a bathroom cleaner. WorkManager works hard so you don't have to.
 *
 * To use the WorkManager, there are three parts. You'll need to describe
 * the background task, or work, to be done, you'll need to create a work request to define the conditions of the work, and finally, you'll need an instance
 * of the WorkManager to schedule the work request.
 *
 */
class MyWorkManager(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        println("before delay")

        delay(1000)
        val data = workDataOf(Pair("cvc", "done"))
        println("after delay")
        for (item in 1..20) {
            delay(1000)
            println("inside loop $item")
        }
        println("after delay final")
        return Result.success(data)
    }
}
package com.example.myapplication.common

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

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
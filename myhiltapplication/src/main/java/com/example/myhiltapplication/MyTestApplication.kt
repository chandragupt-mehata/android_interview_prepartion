package com.example.myhiltapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyTestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
package com.example.myapplication.test

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AeroplaneModeReceiver: BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(p0: Context?, p1: Intent?) {
        println("hello")
        Toast.makeText(p0, "got event for broadcast", Toast.LENGTH_LONG).show()
    }
}
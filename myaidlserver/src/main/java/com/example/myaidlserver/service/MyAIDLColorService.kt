package com.example.myaidlserver.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.myaidlserver.IMyAidlColorInterface

class MyAIDLColorService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    private val binder = object : IMyAidlColorInterface.Stub() {
        override fun getColor(): Int {
            return 15
        }

    }
}
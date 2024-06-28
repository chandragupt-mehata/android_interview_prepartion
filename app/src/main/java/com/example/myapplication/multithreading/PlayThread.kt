package com.example.myapplication.multithreading

import android.os.Handler
import android.os.Looper
import android.os.Message

class PlayThread: Thread() {
    override fun run() {
        super.run()
        Looper.prepare()
        handler = Handler() { msg ->
            println("inside handler: $msg")
            when (msg.what) {
                else -> false
            }
        }
        Looper.loop()
    }
}

private var playThread: PlayThread = PlayThread()
private var handler: Handler? = null

fun initHandler(msg1: Message) {
    handler?.sendMessage(msg1)
}

fun main() {
    playThread.start()
    val msg = Message.obtain()
    msg.what = 123
    initHandler(msg)
}
package com.example.myapplication.multithreading

fun main() {

    //here updateAtTheSameTime locks the object and t2 wont be able to call updateAtTheSameTimeTwo until t1 releases lock
    /*val myStaticClass = MyStaticClass()
    val t1 = Thread {
        println("t1: start")
        myStaticClass.updateAtTheSameTime()
        println("t1: end")
    }
    val t2 = Thread {
        Thread.sleep(10)
        println("t2: start")
        myStaticClass.updateAtTheSameTimeTwo()
        println("t2: end")
    }
    t1.start()
    t2.start()*/


    //here it wont wait because synchronized lock has been applied in different way
    // synchronized over static method locks the static instance of MyStaticClass where as synchronized over normal method i.e. updateAtTheSameTimeTwo
    //will lock instance of MyStaticClass. Both method are locked under different object so method call via diff thread won't wait for each other to finish
    /*val myStaticClass = MyStaticClass()
    val t1 = Thread {
        println("t1: start")
        myStaticClass.updateAtTheSameTimeTwo()
        println("t1: end")
    }
    val t2 = Thread {
        Thread.sleep(10)
        println("t2: start")
        MyStaticClass.updateCounter()
        println("t2: end")
    }
    t1.start()
    t2.start()*/

    //here it will wait because synchronized lock has been applied over same static instance. Although methods are diff
    //but lock has been applied over same instance.
    val t1 = Thread {
        println("t1: start")
        MyStaticClass.updateCounter()
        println("t1: end")
    }
    val t2 = Thread {
        Thread.sleep(10)
        println("t2: start")
        MyStaticClass.updateCounterTwo()
        println("t2: end")
    }
    t1.start()
    t2.start()
}

fun String.myExtension() {
    this.first()
}
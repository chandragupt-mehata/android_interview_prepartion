package com.example.myapplication.multithreading;

//do not modify it
public class MyStaticClass {

    public static int counter = 0;

    public synchronized static void updateCounter() throws InterruptedException {
        counter ++;
        Thread.sleep(1000);
    }

    public synchronized static void updateCounterTwo() {
        counter ++;
    }

    public synchronized void updateAtTheSameTime() throws InterruptedException {
        counter ++;
        Thread.sleep(5000);
    }

    public synchronized void updateAtTheSameTimeTwo() throws InterruptedException {
        counter ++;
    }

}


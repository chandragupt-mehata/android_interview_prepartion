package com.example.myapplication

interface Consumer<in T> {
    fun consume(item: T)
}

class AnyConsumer : Consumer<Any> {
    override fun consume(item: Any) {
        println("Consuming: $item")
    }
}

class Fruit

fun main() {
    // Using contravariance
    val anyConsumer: Consumer<Any> = AnyConsumer()
    val fruitConsumer: Consumer<Fruit> = anyConsumer // This is allowed due to contravariance

    // Now, let's use the fruitConsumer to consume a Fruit
    val fruit: Fruit = Fruit()
    fruitConsumer.consume(fruit)
}

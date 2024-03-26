package com.example.myapplication.test

fun main() {
    val customClass = CustomClass("")
    customClass.customScope {
        it.abc = "hii"
    }
    println("customClass: $customClass")

    customClass.also {
        it.abc = ""
    }
    val z = customClass.customAlsoScope {
        it.abc = "also"
    }
    println("${z === customClass}")

    customClass.apply {
        abc = "cool"
    }
    customClass.customApplyScope {
        abc = "apply"
    }
    println(customClass)

    val string = customClass.let {
        it.abc = "let"
        ""
    }
    val customStr = customClass.customLetScope {
        it.abc = "customLet"
        "customLetScope"
    }

    val runString = customClass.run {
        abc = "run"
        "run"
    }
    val customRunString = customClass.customRunScope {
        abc = "run"
        "run"
    }

    val with = with(customClass) {
        abc = "with"
        "withReturn"
    }
    val withTwo = customWithScope(customClass) {
        abc = "customWith"
        ""
    }
}

/**
 * A custom scope which does not return anything. returns unit
 */
fun <T> T.customScope(lambda: (t: T) -> Unit) {
    lambda(this)
}

/**
 * A custom scope which returns object itself
 */
fun <T> T.customAlsoScope(lambda: (t: T) -> Unit): T {
    lambda(this)
    return this
}

/**
 * A custom scope which returns object itself
 */
fun <T> T.customApplyScope(lambda: T.() -> Unit): T {
    lambda()
    return this
}

/**
 * A custom scope which returns lambda result like let
 */
fun <T, R> T.customLetScope(lambda: (t: T) -> R): R {
     return lambda(this)
}

/**
 * A custom scope which returns lambda result like run
 */
fun <T, R> T.customRunScope(lambda: T.() -> R): R {
    return lambda()
}

/**
 * A custom scope which returns lambda result like with
 */
fun <T, R> customWithScope(with: T, lambda: T.() -> R): R {
    return with.lambda()
}

// some common things
// - use R as second genric if we need to return lambda result as part of scope function
// - simply pass this object to lambda if we want it inside scope function. Yes but of course we will have to create
// extension function as that custom scope function so that we can pass this to lambda
// - if we want to use this then create lambda as extension function of T so that it will have this

data class CustomClass(var abc: String)
package com.example.mytestapplication

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

class NetworkIdlingResource : IdlingResource {

    private val resource = CountingIdlingResource("NetworkIdlingResource")

    override fun getName(): String {
        return resource.name
    }

    override fun isIdleNow(): Boolean {
        return resource.isIdleNow
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        resource.registerIdleTransitionCallback(callback)
    }

    fun increment() {
        resource.increment()
    }

    fun decrement() {
        if (!resource.isIdleNow) {
            resource.decrement()
        }
    }

    companion object {
        // Singleton instance
        private val instance = NetworkIdlingResource()

        fun getInstance(): NetworkIdlingResource {
            return instance
        }
    }
}

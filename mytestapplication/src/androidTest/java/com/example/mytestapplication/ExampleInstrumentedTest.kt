package com.example.mytestapplication

import android.app.Person
import android.service.autofill.OnClickAction
import android.view.View
import android.widget.Button
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get : Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {

    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mytestapplication", appContext.packageName)
    }

    @Test
    fun performButtonTest() {
        onView(withText("how are you"))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.button))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.button))
            .check(matches(withText("how are you")))

        val str = "I am not clicked"
        onView(withId(R.id.button))
            .perform(click())
            .check(matches(customMatcher(str, "")))
    }

    @Test
    fun performButtonTestWithResourceIdealing() {
        IdlingRegistry.getInstance().register(NetworkIdlingResource.getInstance())

        NetworkIdlingResource.getInstance().increment()

        // Trigger a network request
        onView(withId(R.id.button2))
            .perform(click())

        // Perform assertions after the network request is complete
        onView(withText("Verify resource idealing"))
            .check(matches(isDisplayed()))

        // Inform Espresso that the background operation has finished
        NetworkIdlingResource.getInstance().decrement()
    }

    private fun customMatcher(str: String, str2: String): Matcher<View> {
        return object : BoundedMatcher<View, Button>(Button::class.java) {
            override fun describeTo(description: Description?) {

            }

            override fun matchesSafely(item: Button?): Boolean {
                val text = item?.text
                return text?.startsWith("I") ?: false
            }

        }
    }
}
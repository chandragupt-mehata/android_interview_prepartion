package com.example.myapplication.test

/**
 * A test double is an object that can stand in for a real object in a test,
 * similar to how a stunt double stands in for an actor in a movie.
 *
 * Dummy — When we use an object to stand in place of a real object, but never make of the object, then the object is called a Dummy. Its usually done to
 * fill the parameter list, so that the code compiles and the compiler stays happy.
 *
 * Fake — These objects actually have a complete working implementation in them. But the implementation provided in them is some kind of shortcut
 * which helps us in our task of unit testing, and this shortcut renders it incapable in production. A great example of this is the in-memory
 * database object which we can use just for our testing purposes, while we use the real database object in production.
 *
 * In summary, while both the fake and the stub provide a substitute for the real PaymentProcessor, the fake goes a step further by including additional behavior beyond just
 * returning a value. It simulates a more complete, albeit simplified, version of the payment processing behavior, making it useful for testing scenarios
 * where the actual behavior matters more than just the return value. The choice between a fake and a stub depends on the testing requirements and the level
 * of complexity needed for the test double.
 *
 * Stubs — These are objects which we hard code to provide canned responses whenever specific methods are called on those objects.
 * If you have ever used mockito, a mocking framework provided for Java and Android, any object used inside the when/thenReturn or the doReturn/when
 * syntax is basically a stub.
 *
 * Mocks — These are objects which we use for verification by checking whether particular methods were called on the object, with specific parameters.
 * So these objects become the basis for the results of our tests. Again using mockito as an example, any object used inside the verify method is a mock.
 *
 *
 * *************
 * Espresso
 * onView(ViewMatcher), perform(ViewAction), check(ViewAssertion)
 *
 * *************************
 * In summary, Mockito is a mocking framework primarily used for unit testing individual code components in Java applications, while Robolectric is
 * a framework specifically designed for testing Android applications and their components, including UI testing. Mockito focuses on creating mocks
 * and stubs to simulate behavior, while Robolectric allows for the simulation of Android components without the need for an emulator or connected
 * device. Mockito does not directly integrate with the Android SDK, whereas Robolectric provides seamless integration and simulation of Android components.
 */

abstract sealed class abc {

}
class TestDoubles {

    val abc by lazy {
        ""
    }

}
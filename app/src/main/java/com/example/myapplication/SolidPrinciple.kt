package com.example.myapplication

//https://proandroiddev.com/solid-design-principles-in-kotlin-79100c670df1

/**
 * The SOLID principles are a set of design principles in object-oriented programming that
 * can help create more understandable, flexible, and maintainable software.
 *
 * 1. Single Responsibility Principle (SRP)
 * A class should have only one reason to change, meaning it should have only one job or responsibility.
 *
 *
 */
class User(val name: String, val email: String)

class UserRepository {
    fun save(user: User) {
        // Code to save user to the database
    }
}

class UserEmailService {
    fun sendWelcomeEmail(user: User) {
        // Code to send a welcome email to the user
    }
}

fun main() {
    // Usage
    val user = User("John Doe", "john.doe@example.com")
    val userRepository = UserRepository()
    userRepository.save(user)

    val emailService = UserEmailService()
    emailService.sendWelcomeEmail(user)
}

/**
 * 2. Open/Closed Principle (OCP)
 * Software entities (classes, modules, functions, etc.) should be open for extension but closed for modification.
 * Open for extension - Extend existing behaviour
 * Closed for modification - Existing code remains unchanged. Only additional functionality can be added without modification in existing behaviour.
 */

/**
 * The code below has a class named area factory which calculates the area of a shape. Having a close look at the code we see that we have an
 * if-else statement to separate out the shapes, and as the shapes increase they will continue to grow that way Because the class is not closed
 * for modification, and neither it is open for extension. Hence it is violating our open-closed principle.
 */

//class Rectangle {
//    private var length;
//    private var height;
//    // getters/setters ...
//}
//
//class Circle {
//    private var radius;
//    // getters/setters ...
//}
//
//class AreaFactory {
//    public fun calculateArea(shapes: ArrayList<Object>): Double {
//        var area = 0;
//        for (shape in shapes) {
//            if (shape is Rectangle) {
//                var rect = shape as Rectangle;
//                area += (rect.getLength() * rect.getHeight());
//            } else if (shape is Circle) {
//                var circle = shape as Circle;
//                area += (circle.getRadius() * cirlce.getRadius() * Math.PI);
//            } else {
//                throw new RuntimeException("Shape not supported");
//            }
//        }
//        return area;
//    }
//}

//here without enum if we will have dedicated class also then we will end up with sending that class object to send notification function and then
//based on type we do need to process the notification.
enum class NotificationEnum {
    PUSH_NOTIFICATION, EMAIL, SMS
}

class NotificationService {

    fun sendNotification(notification: NotificationEnum) {
        when (notification) {
            NotificationEnum.PUSH_NOTIFICATION -> {
                // send push notification
            }

            NotificationEnum.EMAIL -> {
                // send email notification
            }

            NotificationEnum.SMS -> {
                // send sms notification
            }
        }
    }
}

interface Notification {
    fun sendNotification()
}

class PushNotification : Notification {
    override fun sendNotification() {
        // send push notification
    }
}

class EmailNotification : Notification {
    override fun sendNotification() {
        // send email notification
    }
}

class NotificationServiceNew {

    fun sendNotification(notification: Notification) {
        notification.sendNotification()
    }
}

/**
 * 3. Liskov Substitution Principle (LSP)
 * Objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program.
 */

open class Bird {
    open fun fly() {
        println("Flying")
    }

    open fun eat() {
        println("Bird is eating")
    }
}

class Sparrow : Bird()

class Penguin : Bird() {
    override fun fly() {
        throw UnsupportedOperationException("Penguins can't fly")
    }

    override fun eat() {
        super.eat()
    }
}

fun letTheBirdFly(bird: Bird) {
    bird.fly()
}

fun main1() {
    open class Bird {
        open fun fly() {
            println("Flying")
        }
    }

    class Sparrow : Bird()

    class Penguin : Bird() {
        override fun fly() {
            throw UnsupportedOperationException("Penguins can't fly")
        }
    }

    fun letTheBirdFly(bird: Bird) {
        bird.fly()
    }

// Usage
    val sparrow = Sparrow()
    letTheBirdFly(sparrow) // Works fine

    val penguin = Penguin()
    letTheBirdFly(penguin) // Throws exception, violates LSP
}

// To adhere to LSP, we should separate behaviors that not all birds can perform into different classes or interfaces.
interface FlyingBird {
    fun fly()
}

open class BirdNew {
    fun eat() {
        println("Bird is eating")
    }
}

class SparrowNew : BirdNew(), FlyingBird {
    override fun fly() {
        println("Sparrow is flying")
    }
}

class PenguinNew : BirdNew() {
    // Penguins don't implement FlyingBird because they can't fly
}

fun letBirdEat(bird: BirdNew) {
    bird.eat()
}

fun letBirdFly(flyingBird: FlyingBird) {
    flyingBird.fly()
}

fun main5() {
    val sparrow = SparrowNew()
    val penguin = PenguinNew()

    // Both sparrow and penguin can eat
    letBirdEat(sparrow)
    letBirdEat(penguin)

    // Only sparrow can fly
    letBirdFly(sparrow)

    // letBirdFly(penguin) // This would cause a compile-time error, as Penguin does not implement FlyingBird
}




/**
 * 4. Interface Segregation Principle (ISP)
 * A client should not be forced to depend on interfaces it does not use.
 */
interface Printer {
    fun print(document: String)
}

interface Scanner {
    fun scan(): String
}

class MultifunctionPrinter : Printer, Scanner {
    override fun print(document: String) {
        println("Printing: $document")
    }

    override fun scan(): String {
        return "Scanned Document"
    }
}

class SimplePrinter : Printer {
    override fun print(document: String) {
        println("Printing: $document")
    }
}

fun main2() {
    // Usage
    val printer: Printer = SimplePrinter()
    printer.print("Hello, World!")
}

/**
 * 5. Dependency Inversion Principle (DIP)
 * High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details.
 * Details should depend on abstractions.
 */

interface AuthService {
    fun authenticate(user: String, password: String): Boolean
}

class OAuthService : AuthService {
    override fun authenticate(user: String, password: String): Boolean {
        // LDAP authentication logic
        return true
    }
}

class JWTService : AuthService {
    override fun authenticate(user: String, password: String): Boolean {
        // LDAP authentication logic
        return true
    }
}

class BasicAuthService : AuthService {
    override fun authenticate(user: String, password: String): Boolean {
        // LDAP authentication logic
        return true
    }
}

class LoginController(private val authService: AuthService) {
    fun login(user: String, password: String): Boolean {
        return authService.authenticate(user, password)
    }
}

fun main3() {
    // Usage
    val authService = OAuthService()
    val controller = LoginController(authService)
    val isAuthenticated = controller.login("user", "password")
    println("Authenticated: $isAuthenticated")
}

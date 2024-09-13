package com.example.myapplication.test

import androidx.fragment.app.Fragment

/**
 * Creational patterns: Concern the process of object creation. Structural patterns: Deal with the composition of objects or classes. Behavioral patterns:
 * Characterize the ways in which classes or objects interact and distribute responsibility.
 */
/**
 * https://www.udemy.com/course/solid-design/learn/lecture/16123199#overview
 * Cohesion is the degree to which various component is related to each other.
 * Coupling is defined as the level of interdependency between various component.
 */

/**
 * Singleton : It restricts the creation of an object to only a single instance.
 *
 */

//https://chatgpt.com/share/fa409d37-5c08-4ebb-aea7-37ba59a228b1

fun main() {
    val builderParentClass = BuilderParentClass(s1 = "", s2 = null)
}

class BuilderParentClass(val s1: String, val s2: String?, val s3: String = "", val s4: String? = null) {

}

class BuilderParentClassTwo(val s1: String, val s2: String?, val s3: String = "", val s4: String? = null) {

}

/**
 * Factory design pattern: it provides an interface to create the object in super class but allows subclasses to alter the type of the object that will be created.
 * Wrong way : We can introduce one more abstract func like create(fun createVehicle(): Vehicle) in Vehicle interface and provide the implementation in Car and Truck
 * class but it will break the Interface segregation principle so it's good to have a factory interface and implementation for creating the objects in mpbgdc.
 */

interface Vehicle {
    fun drive()
}

class Car: Vehicle {
    override fun drive() {
        TODO("Not yet implemented")
    }

}

class Truck: Vehicle {
    override fun drive() {
        TODO("Not yet implemented")
    }

}

//Factory interface
interface VehicleFactory {
    fun createVehicle(): Vehicle
}

class CarFactory: VehicleFactory {
    override fun createVehicle(): Vehicle {
        return Car()
    }
}

class TruckFactory: VehicleFactory {
    override fun createVehicle(): Vehicle {
        return Truck()
    }
}

fun operation() {
    val vehicle = CarFactory().createVehicle()
    vehicle.drive()
}


//Step 1: Define the Fragment subclasses
class HomeFragment : Fragment() {
    // Implementation for HomeFragment
}

class SettingsFragment : Fragment() {
    // Implementation for SettingsFragment
}

class ProfileFragment : Fragment() {
    // Implementation for ProfileFragment
}

//Step 2: Define the Factory Interface
interface FragmentFactory {
    fun createFragment(type: String): Fragment
}


// Step 3: Implement the Factory Interface
class FragmentFactoryImpl : FragmentFactory {

    companion object {
        const val HOME_FRAGMENT = "HomeFragment"
        const val SETTINGS_FRAGMENT = "SettingsFragment"
        const val PROFILE_FRAGMENT = "ProfileFragment"
    }

    override fun createFragment(type: String): Fragment {
        return when (type) {
            HOME_FRAGMENT -> HomeFragment()
            SETTINGS_FRAGMENT -> SettingsFragment()
            PROFILE_FRAGMENT -> ProfileFragment()
            else -> throw IllegalArgumentException("Unknown Fragment type")
        }
    }
}


// Above example is having one loop hole that for each new fragment createFragment method need to be updated which is violation of open/closed principle.
// to overcome we can use factory provider implementation

class SettingFragmentFactory: FragmentFactory {
    override fun createFragment(type: String): Fragment {
        TODO("Not yet implemented")
    }

}

class HomeFragmentFactory: FragmentFactory {
    override fun createFragment(type: String): Fragment {
        TODO("Not yet implemented")
    }

}

class ProfileFragmentFactory: FragmentFactory {
    override fun createFragment(type: String): Fragment {
        TODO("Not yet implemented")
    }

}

class FragmentFactoryProvider {
    private val fragmentProviderMap = mutableMapOf<String, FragmentFactory>()

    fun register(type: String, fragmentFactory: FragmentFactory) {
        fragmentProviderMap[type] = fragmentFactory
    }

    fun getFragment(type: String): Fragment? {
        return fragmentProviderMap[type]?.createFragment(type)
    }
}

val fragmentFactoryProvider = FragmentFactoryProvider()

fun register() {
    fragmentFactoryProvider.register("HOME", HomeFragmentFactory())
    fragmentFactoryProvider.register("PROFILE", ProfileFragmentFactory())
    fragmentFactoryProvider.register("SETTINGS", SettingFragmentFactory())
}

fun caller() {
    val home = fragmentFactoryProvider.getFragment("HOME")
}




/**
 * Builder Pattern: It provides a flexible way to create complex object.
 */

/**
 * Abstract Factory Design Pattern: It lets a factory create a family of related objects without knowing the concrete class.
 */

/**
 * Adapter design pattern -
 * A structural design pattern using which we make our existing object work with client by adapting the object to client's expected interface.
 * Adapter extends new existing object implements old client expected interface
 *
 * Client uses specific interface.
 * New class does not implement that interface.
 * So if client need to use that new class then client code need to be changed. To avoid that one wrapper class like adapter can be created
 * which will have same abstract method which client needs. But in that abstract fun - implementation will call new class impl
 *
 * Calling code - Initial library : two fnc createOrderItem() and placeOrder()
 * Now new library came with : two fnc addOrderItem and generateOrder
 * If client need to consume new library in stead of older one then client code need to be changed to avoid that
 * New Library Adapter class : same fnc two fnc createOrderItem() and placeOrder() -> And these fnc will call addOrderItem and generateOrder
 *
 */

/**
 * Prototype Design Pattern: It's a creational design pattern where one object is used as prototypical instance to create other object.
 * Example Data class copy shallow/deep copy
 */

/*
*********************************
* Structural Design Pattern
 */

/**
 * Decorator Design Pattern: The Decorator pattern is a structural pattern that allows behavior to be added to an individual object, dynamically, without
 * affecting the behavior of other objects from the same class.
 *
 * Behaviors can be added or removed dynamically at runtime by wrapping objects with decorators.
 * This provides greater flexibility and avoids the need for a large number of subclasses.
 *
 * Key Differences
 * Flexibility:
 *
 * Inheritance: Extends functionality at compile-time. Once a subclass is created, it cannot change its superclass or add/remove behaviors dynamically.
 * Decorator: Extends functionality at runtime. You can wrap an object with multiple decorators dynamically.
 * Class Explosion:
 *
 * Inheritance: Can lead to a large number of subclasses to cover all combinations of behavior.
 * Decorator: Avoids class explosion by combining decorators as needed.
 * Object Composition:
 *
 * Inheritance: Uses inheritance, creating a tightly coupled hierarchy.
 * Decorator: Uses composition, allowing more flexible and loosely coupled designs.
 * Single Responsibility Principle:
 *
 * Inheritance: Subclasses might end up doing more than one thing, violating the single responsibility principle.
 * Decorator: Each decorator class focuses on one specific behavior.
 *
 * https://chatgpt.com/share/ad3375ee-27a5-4020-8684-96d577194725
 */

open class Text {
    open fun format(): String {
        return "Plain Text"
    }
}

open class BoldText : Text() {
    override fun format(): String {
        return "<b>${super.format()}</b>"
    }
}

class ItalicText : Text() {
    override fun format(): String {
        return "<i>${super.format()}</i>"
    }
}

class UnderlineText : Text() {
    override fun format(): String {
        return "<u>${super.format()}</u>"
    }
}

open class BoldItalicText : BoldText() {
    override fun format(): String {
        return "<i>${super.format()}</i>"
    }
}

class BoldItalicUnderlineText : BoldItalicText() {
    override fun format(): String {
        return "<u>${super.format()}</u>"
    }
}

//now with decorator
/*interface Text {
    fun format(): String
}

class PlainText : Text {
    override fun format(): String {
        return "Plain Text"
    }
}

open class TextDecorator(private val text: Text) : Text {
    override fun format(): String {
        return text.format()
    }
}

class BoldDecorator(text: Text) : TextDecorator(text) {
    override fun format(): String {
        return "<b>${super.format()}</b>"
    }
}

class ItalicDecorator(text: Text) : TextDecorator(text) {
    override fun format(): String {
        return "<i>${super.format()}</i>"
    }
}

class UnderlineDecorator(text: Text) : TextDecorator(text) {
    override fun format(): String {
        return "<u>${super.format()}</u>"
    }
}


//usage
val plainText: Text = PlainText()
println(plainText.format()) // Output: Plain Text

val boldText: Text = BoldDecorator(plainText)
println(boldText.format()) // Output: <b>Plain Text</b>

val italicBoldText: Text = ItalicDecorator(boldText)
println(italicBoldText.format()) // Output: <i><b>Plain Text</b></i>

val underlineItalicBoldText: Text = UnderlineDecorator(italicBoldText)
println(underlineItalicBoldText.format()) // Output: <u><i><b>Plain Text</b></i></u>

*/




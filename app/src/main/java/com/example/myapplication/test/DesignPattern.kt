package com.example.myapplication.test

/**
 * Creational patterns: Concern the process of object creation. Structural patterns: Deal with the composition of objects or classes. Behavioral patterns:
 * Characterize the ways in which classes or objects interact and distribute responsibility.
 */
fun main() {
    val builderParentClass = BuilderParentClass(s1 = "", s2 = null)
}

class BuilderParentClass(val s1: String, val s2: String?, val s3: String = "", val s4: String? = null) {

}

class BuilderParentClassTwo(val s1: String, val s2: String?, val s3: String = "", val s4: String? = null) {

}

/**
 * Factory design pattern: it provides an interface to create the object in super class but allows subclasses to alter the type of the object that will be created.
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
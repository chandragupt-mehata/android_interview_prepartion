package com.example.myapplication

/**
 * https://chatgpt.com/share/7f8d1afa-dc6b-4d57-8e6b-751d37981075
 * https://proandroiddev.com/serializable-or-parcelable-why-and-which-one-17b274f3d3bb
 *
 * Before comparing Java Serializable and Android Pacelable together, let’s find the answer to this question. “In case of transferring objects between Android
 * components, why do we have to convert them to the Serializable or Parcelable?”
 *
 * When an application is in the background, its process may be killed by the OS in case of low memory. And by returning back the
 * user to the app, the OS creates a new process for the application. Therefore, with passing an instance of the object to the Bundle, when the process is
 * changed, the reference of the object won’t be in the new process and it wouldn’t be possible to use that object. Also, in the case of PendingIntents, if the
 * owing application is killed, the PendingIntent itself will remain usable from other processes that have been given it.
 * So, the OS instead of saving the reference of an object must save its values and it would be possible just by converting that object to the Parcelable or
 * Serializable.
 */


/**
 *
 * In Android, serialization is the process of converting an object into a format that can be easily stored,
 * transmitted, or reconstructed. The Serializable interface in Java and Android is used to mark a class as
 * serializable, indicating that instances of that class can be converted into a byte stream.
 * This byte stream can then be saved to a file, sent over a network, or otherwise persisted.
 *
 *
 *****************************
 *
 * I apologize for any confusion. To clarify, I should have been more precise in my language. While
 * simple types like String, int, and char can be passed between activities using Intent extras without explicit
 * serialization, it doesn't mean that they are serializable in the sense of implementing the Serializable
 * interface.
 *
 * In the context of Android, when you use Intent extras to pass simple types, the Android framework
 * automatically handles the serialization and deserialization process for you. Android uses its
 * own mechanism to transfer these simple types between components (activities, services, etc.), and
 * you don't need to explicitly implement Serializable or any other serialization interface for these basic types.
 *
 * To clarify further:
 *
 * Simple types (e.g., String, int, char): These types are "parcelable" by default in Android.
 * The term "parcelable" is used here in a broader sense, not necessarily tied to the Parcelable interface.
 * Android handles the serialization and deserialization of these types internally.
 *
 * Complex types or custom objects: When dealing with complex types or custom objects, especially
 * when passing them between components, you typically need to make them implement Serializable or Parcelable
 * explicitly to provide a standardized way for Android to serialize and deserialize them.
 *
 * So, to correct my previous statement: Simple types like String don't implement the Serializable
 * interface, but they are handled by Android's internal serialization mechanism when passed as Intent extras.
 * If you're interested in the technical details of how this works in Android, you might want to explore the
 * Android source code or documentation related to interprocess communication and Intent extras handling.
 *
 *
 * ************************************
 * does not both object get converted into byte stream while being trasfered from one component to another ?
 * ????????????????
 *
 * You are correct; I appreciate the clarification. In the context of Parcelable, the
 * term "byte stream" might be a bit misleading, as Parcelable does not directly produce a
 * traditional byte stream like Serializable. Instead, it uses a Parcel object, which is a high-performance
 * container for serialized data, optimized for Android interprocess communication (IPC).
 *
 * So, while writeToParcel writes the object's properties to a Parcel, the Parcel itself can be considered a
 * form of serialization. It's a way of efficiently packaging the data for transportation without the same overhead
 * as traditional Java serialization.
 *
 * To make the distinction clearer:
 *
 * Traditional Serialization (Serializable):
 *
 * Uses a byte stream.
 * Involves converting an object's state into a sequence of bytes.
 * Java's default serialization mechanism often involves reflection and can be relatively resource-intensive.
 * Parcelable Serialization:
 *
 * Uses a Parcel object.
 * writeToParcel writes the object's properties to the Parcel.
 * The Parcel itself is an optimized container for serialized data in Android, designed for efficiency in IPC
 * scenarios.
 * In essence, while the Parcel is not a traditional byte stream, it serves a similar purpose:
 * efficiently packaging and transporting the data. The specific optimizations in Parcel make it more suitable for
 * Android's use cases, especially when passing data between components like activities or services.
 */

/**
 * It meant when we pass object across diff component that would be copy of original instance or you may say that that would be information
 * of that object. And using that information new object will be created in receiving component. Right or wrong ?
 * ChatGPT
 * When you pass an object across different components in Android, such as activities or services, the object itself is not passed directly. Instead,
 * a copy or information representing that object is passed. Here’s a breakdown of how this process typically works:
 *
 * Passing Objects Between Components
 * Serialization or Parcelization:
 *
 * Before passing an object through an Intent or storing it in a Bundle (e.g., during activity transitions), the object is serialized
 * (if implementing Serializable) or parcelized (if implementing Parcelable).
 * Serialization or parcelization converts the object into a format that can be easily transmitted across component boundaries (like between
 * different processes or during system events).
 * Object Representation:
 *
 * What gets passed between components is not the original object instance itself, but rather a serialized representation of its state.
 * This representation includes the object's data fields and their values, which are encoded into a byte stream (for serialization) or structured data
 * (for parcelization).
 * Reconstruction in Receiving Component:
 *
 * In the receiving component (e.g., another activity receiving data via an Intent), the serialized data is used to reconstruct a new instance of the object.
 * This new instance has the same state as the original object when it was serialized or parcelized.
 * Key Points
 * State Transfer: The primary purpose of serialization or parcelization is to transfer the state (data) of an object between components, not
 * the object instance itself.
 * Efficiency: By transmitting only the necessary data (object state), serialization and parcelization optimize memory usage and improve performance
 * compared to passing entire object instances.
 * Implementation Requirement: Objects must implement Serializable or Parcelable for the Android framework to serialize or parcelize them effectively.
 * Example Scenario
 * kotlin
 * Copy code
 * @Parcelize
 * data class User(val name: String, val age: Int) : Parcelable
 *
 * // Sending activity
 * val user = User("Alice", 25)
 * val intent = Intent(this, ReceiverActivity::class.java)
 * intent.putExtra("user", user)
 * startActivity(intent)
 *
 * // Receiving activity
 * val receivedUser = intent.getParcelableExtra<User>("user")
 * In this example, User implements Parcelable, so when user is passed via an Intent, Android serializes it into a Parcel.
 * In the receiving activity (ReceiverActivity), receivedUser is reconstructed as a new User object with the same data as the original user object.
 * Conclusion
 * When passing objects between different components in Android, serialization or parcelization ensures that the state (data) of the object is transferred
 * accurately while maintaining efficiency and performance. The receiving component reconstructs a new instance of the object based on the
 * serialized or parcelized data, rather than directly receiving the original object instance. This approach helps maintain application state
 * consistency and facilitates seamless inter-component communication in Android applications.
 *
 *
 *
 *
 *
 *
 *
 */

class SerializableVsParcellable {
}
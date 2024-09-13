package com.example.myapplication.springboot

/**
 * *.kt -> kotlin compiler -> *.class -> *.jar -> JVM
 * JVM only understands byte code and it converts byte code to machine language
 *
 * https://medium.com/programming-lite/android-core-jvm-dvm-art-jit-aot-855039a9a8fa
 *
 * 1. During the Build Process (No Role of DVM/JVM)
 * When you build an APK, the key steps are:
 * Kotlin Compiler (kotlinc): Compiles Kotlin source code (.kt files) into Java bytecode (.class files).
 * D8 or R8: Converts Java bytecode (.class) into Dalvik bytecode (.dex), optimized for Android.
 * APK Packaging: The .dex files (Dalvik bytecode), resources, and other assets are bundled into an APK file.
 * No Role of JVM or DVM:
 * The build process does not involve either the JVM or DVM directly. Instead, it's just the Kotlin/Java compiler and the Android build tools (D8/R8) that handle code transformation and packaging.
 * R8, specifically, is used for code shrinking, obfuscation, and optimization while generating .dex files from Java bytecode.
 * 2. After Installation: DVM/ART's Role (Execution)
 * Once the APK is installed on the Android device, Dalvik or ART (Android Runtime) plays its critical role in executing the code:
 *
 * a. Dalvik VM (Older Android Versions):
 * In Android versions before 5.0 (Lollipop), the Dalvik Virtual Machine (DVM) interpreted and executed the .dex bytecode.
 * JIT (Just-in-Time) Compilation: Dalvik compiled parts of the .dex bytecode into machine code as the app ran. This process was slower because the app needed to be compiled on the fly.
 * b. ART (Modern Android Versions):
 * Starting from Android 5.0 (Lollipop), the Android Runtime (ART) replaced Dalvik and introduced Ahead-Of-Time (AOT) compilation.
 * AOT Compilation: After the APK is installed, ART converts the .dex bytecode into native machine code specific to the device's CPU architecture during installation.
 * JIT Optimization: ART also uses JIT to optimize parts of the code while the app is running, but most of the code is precompiled.
 * Summary of Roles:
 * During Build (APK creation):
 *
 * No role for JVM or DVM/ART.
 * The build process relies on the Kotlin/Java compiler to generate Java bytecode and tools like D8/R8 to convert that bytecode into Dalvik bytecode (.dex).
 * After Installation (APK execution):
 *
 * DVM/ART is responsible for executing the code on the Android device.
 * DVM (in older versions) uses JIT to convert .dex to machine code at runtime.
 * ART (in newer versions) uses AOT to compile .dex to machine code during installation, with some JIT optimizations at runtime.
 * The Role of R8:
 * R8 plays an important part during the build process by:
 * Shrinking the code (removing unused methods and classes).
 * Obfuscating the code (renaming classes, methods, and fields).
 * Optimizing the code for performance.
 * Converting the bytecode into Dalvik bytecode (.dex).
 * R8 ensures that your code is leaner, harder to reverse-engineer, and ready to run efficiently on an Android device, but it does not interact with DVM/ART directly.
 *
 * Conclusion:
 * The JVM/DVM has no role in the build process of the APK.
 * Their roles are vital after installation, where they are responsible for executing the .dex files on the Android device.
 * The build process primarily involves the Kotlin compiler, D8/R8, and packaging tools, while the DVM/ART handles the execution of the app on the device after installation.
 */
class KotlinBuildProcess {
}
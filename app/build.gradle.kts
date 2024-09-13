import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("jacoco")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            enableUnitTestCoverage = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.register("Hello") {
    doFirst {
        println("hello entering inside tasks")
    }
    doLast {
        println("hello exiting inside tasks")
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    //implementation(project(mapOf("path" to ":myhiltapplication")))
    //implementation(project(":myhiltapplication"))
    implementation ("androidx.security:security-crypto:1.1.0-alpha06")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

task("helloWorld") {
    doFirst {
        println("hello entering inside tasks of hello world")
    }
    doLast {
        println("end of the task")
    }
}

tasks.whenTaskAdded {
    if (name == "assemble" || name == "assembleDebug") {
        dependsOn("helloWorld")
    }
}

afterEvaluate() {
    tasks.named("helloWorld") {
        enabled = false // Disable the task after it's been fully configured
    }
}

/**
 * The reason afterEvaluate works in this context is due to the way Gradle handles task configuration and dependency management. Here’s a detailed breakdown:
 *
 * Understanding Gradle's Configuration and Execution Phases
 * Configuration Phase:
 *
 * During this phase, Gradle evaluates the build script, sets up tasks, and configures them.
 * This is where println statements in the task definition will run. These are executed even if the task is later disabled because they are part of the task configuration, not execution.
 * Execution Phase:
 *
 * This phase occurs after the configuration phase and is where tasks are actually run.
 * The enabled property is checked during this phase to determine if the task should be executed.
 * Why afterEvaluate Works
 * Timing:
 * afterEvaluate is a lifecycle hook that runs after Gradle has fully evaluated the project and all tasks have been configured. This means you can safely modify task properties or manage dependencies knowing that all tasks are already available and their configurations are complete.
 * Task Dependency Management:
 * By using afterEvaluate, you ensure that any modifications to tasks, such as setting enabled to false or adding dependencies, are applied after Gradle has already set up the task graph. This prevents issues where tasks might be executed before they are disabled or dependencies are set.
 * Why whenTaskAdded Might Not Work
 * Timing Issues:
 * The whenTaskAdded block is triggered each time a new task is added to the project. However, this might happen during the configuration phase, which can be too early for making changes like setting enabled to false if the task is already in the process of being configured or executed.
 * Task Lifecycle:
 * If tasks are added dynamically by plugins or other parts of the build script, whenTaskAdded might not guarantee that the task is fully configured or that all dependencies have been set correctly.
 * Practical Summary
 * Use afterEvaluate for tasks that need to be configured based on other tasks or project state, especially when you need to ensure all tasks are fully set up before making changes.
 * Use whenTaskAdded when you need to make changes to tasks as they are created, but be aware of its limitations related to task configuration timing.
 * By using afterEvaluate, you’re ensuring that all tasks are fully configured and available for any modifications, making it a safer and more reliable choice in this scenario.
 */

/*tasks.whenTaskAdded {
    if (name == "helloWorld") {
        enabled = false
    }
}*/

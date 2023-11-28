plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    val minSDK: Int by extra { 26 }
    val targetSDK: Int by extra { 34 }

    namespace = "task.swenson.eventplanner"
    compileSdk = targetSDK

    defaultConfig {
        applicationId = "task.swenson.eventplanner"
        minSdk = minSDK
        targetSdk = targetSDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// App Dependencies (A-Z)
val androidJunit by extra { "1.1.5" }
val appCompat by extra { "1.6.1" }
val coroutinesTest by extra { "1.5.2" }
val espresso by extra { "3.5.1" }
val junit by extra { "4.13.2" }
val ktx by extra { "1.12.0" }
val material by extra { "1.10.0" }
val okHttp by extra { "5.0.0-alpha.6" }
val retrofit by extra { "2.9.0" }
val timber by extra { "5.0.1" }
val truth by extra { "1.1.3" }

dependencies {

    implementation("androidx.core:core-ktx:$ktx")

    // UI
    implementation("androidx.appcompat:appcompat:$appCompat")
    implementation("com.google.android.material:material:$material")

    // Testing
    testImplementation("junit:junit:$junit")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesTest")
    androidTestImplementation("androidx.test.ext:junit:$androidJunit")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso")

    // Truth for assertions
    testImplementation("com.google.truth:truth:$truth")
    androidTestImplementation("com.google.truth:truth:$truth")

    // Retrofit & OkHttp
    implementation("com.squareup.okhttp3:okhttp:$okHttp")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttp")
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    testImplementation("com.squareup.okhttp3:mockwebserver:$okHttp")

    // Logging
    implementation("com.jakewharton.timber:timber:$timber")
}
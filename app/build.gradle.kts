plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.apollo)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dagger)
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.android_practice_1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.android_practice_1"
        minSdk = 26
        targetSdk = 35
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
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    apollo {
        service("first_time") {
            packageName.set("src.main.graphql")
            introspection {
                endpointUrl = "https://countries.trevorblades.com/"
                schemaFile.set(file("src/main/graphql/schema.sdl"))
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    // retrofit converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // okhttp
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // Navigation Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // apollo
    implementation(libs.apollo.runtime)

    // dagger
    implementation(libs.dagger.hilt)
    implementation(libs.hilt.compose.navigation)
    kapt(libs.dagger.kapt)

    // navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization)

    // paging
    implementation("androidx.paging:paging-runtime:3.3.2")

    // crop image
    implementation("com.vanniktech:android-image-cropper:4.6.0")

    // ottu sdk
    implementation("com.github.ottuco:ottu-android-checkout:1.0.6")

    // Map Screen
    implementation ("com.google.android.gms:play-services-maps:19.1.0")
    implementation ("com.google.android.libraries.places:places:4.1.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Jackson dependencies
    implementation("com.fasterxml.jackson.core:jackson-core:2.13.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")

}
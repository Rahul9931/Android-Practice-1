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
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.android_practice_1"
        minSdk = 24
        targetSdk = 34
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
    /*apollo {
        service("first_time") {
            packageName.set("src.main.graphql")
            introspection {
                endpointUrl = "https://countries.trevorblades.com/"
                schemaFile.set(file("src/main/graphql/com/example/android_practice_1/schema.sdl"))
            }
        }
    }*/
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

}
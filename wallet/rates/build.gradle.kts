plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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

    kapt {
        correctErrorTypes = true
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    implementation(Dependencies.Kotlin.Coroutines.core)

    implementation(Dependencies.Android.roomKtx)
    implementation(Dependencies.Android.roomRuntime)
    kapt(Dependencies.Android.roomCompiler)

    // Network
    implementation(Dependencies.Android.OkHttp.core)
    implementation(Dependencies.Android.OkHttp.logging)
    implementation(Dependencies.Android.Retrofit.core)
    implementation(Dependencies.Android.Retrofit.gson)
    implementation(Dependencies.Android.Retrofit.scalars)
    implementation(Dependencies.Android.Retrofit.adapter)
    implementation(Dependencies.Android.gson)

    // Dependency Injection
    implementation(Dependencies.Koin.core)
}
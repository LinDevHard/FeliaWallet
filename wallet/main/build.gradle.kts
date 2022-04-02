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
    implementation(project(":wallet:core"))
    implementation(project(":wallet:ethereum"))
    implementation(project(":wallet:rates"))
    implementation(project(":wallet:assets"))
    implementation(project(":utils"))

    implementation(Dependencies.Kotlin.Coroutines.core)

    implementation(Dependencies.Android.roomKtx)
    implementation(Dependencies.Android.roomRuntime)
    kapt(Dependencies.Android.roomCompiler)

    // Dependency Injection
    implementation(Dependencies.Koin.core)

    with(Dependencies.Blockchain) {
        implementation(web3j)
    }
}
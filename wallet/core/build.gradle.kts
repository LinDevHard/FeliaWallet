import com.google.protobuf.gradle.* // ktlint-disable no-wildcard-imports

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.protobuf")
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
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.0"
    }

    sourceSets {
        projectDir
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(Dependencies.Kotlin.Coroutines.core)
    implementation(Dependencies.Android.tink)
    implementation(Dependencies.Android.DataStore.proto)
    implementation(Dependencies.Android.protobuf)

    // Dependency Injection
    implementation(Dependencies.Koin.core)

    api(Dependencies.Blockchain.hdWallet) {
        exclude("com.google.protobuf")
    }
}

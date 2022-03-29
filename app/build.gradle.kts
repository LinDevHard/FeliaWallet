import Dependencies.ArkIvanov.Decompose.decompose
import Dependencies.ArkIvanov.Decompose.extensionsCompose
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = "com.lindevhard.felia"
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(
            project.rootProject.file("local.properties")
                .reader()
        )
        buildConfigField(
            "String",
            "cmcApiKey",
            properties.getProperty("cmcApiKey")
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    packagingOptions {
        resources {
            excludes += mutableSetOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/licenses/ASM",
                "META-INF/DEPENDENCIES"
            )
            pickFirsts += mutableSetOf(
                "protobuf.meta",
                "field_mask.proto",
                "win32-x86-64/attach_hotspot_windows.dll",
                "win32-x86/attach_hotspot_windows.dll"
            )
        }
    }
}

dependencies {
    implementation(project(":compose-ui"))

    implementation(project(":wallet:core"))
    implementation(project(":wallet:assets"))
    implementation(project(":wallet:rates"))
    implementation(project(":wallet:ethereum"))

    // Core Functionality
    with(Dependencies.Android) {
        implementation(coreKtx)
        implementation(appCompat)
        implementation(material)
        implementation(lifecycleRuntimeKtx)
    }

    // MVI
    with(Dependencies.ArkIvanov.MVIKotlin) {
        implementation(mviKotlin)
        implementation(mviKotlinMain)
        implementation(mviKotlinExtensionsCoroutines)
        implementation(rx)
    }

    // Testing
    with(Dependencies.Android) {
        testImplementation(junit)
        testImplementation(testArchCore)
        testImplementation(junitTest)
        testImplementation(testExtJUnitKtx)
        testImplementation(turbine)
        testImplementation(coroutineTest)

        androidTestImplementation(junitTest)
        androidTestImplementation(espressoCore)
        androidTestImplementation(testCoreKtx)
        androidTestImplementation(testArchCore)
        androidTestImplementation(turbine)
    }

    // Testing Compose
    with(Dependencies.Android) {
        androidTestImplementation(junitCompose)
        debugImplementation(composeTooling)
    }

    // Compose
    with(Dependencies.Android) {
        implementation(composeUi)
        implementation(composeAnimation)
        implementation(composeMaterial)
        implementation(composePreview)
        implementation(composeActivity)
        implementation(composeViewModel)
        implementation(composeMaterialIconsCore)
        implementation(composeMaterialIconsExtended)
        implementation(composeFoundation)
        implementation(composeFoundationLayout)
        implementation(composeConstraintLayout)
    }

    // Timber
    implementation(Dependencies.Android.timber)

    // Dependency Injection
    with(Dependencies.Koin) {
        implementation(android)
        implementation(compose)
    }

    // Coil Image loader
    implementation(Dependencies.Android.coilImage)


    // Accompanist
    with(Dependencies.Android) {
        implementation(accompanistInsets)
    }

    with(Dependencies.ArkIvanov) {
        implementation(decompose)
        implementation(extensionsCompose)
    }
    // Splash Screen
    implementation(Dependencies.Android.splashScreenCore)

    with(Dependencies.Blockchain) {
        implementation(hdWallet)
    }

}
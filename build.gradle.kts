buildscript {
}

plugins {
    id("com.android.application").version("7.1.0").apply(false)
    id("com.android.library").version("7.1.0").apply(false)
    id("org.jetbrains.kotlin.android").version("1.6.10").apply(false)
    id("com.google.protobuf").version("0.8.18").apply(false)
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
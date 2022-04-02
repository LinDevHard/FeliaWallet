pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.google.protobuf") {
                useModule("com.google.protobuf:protobuf-gradle-plugin:0.8.18")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(uri("https://jitpack.io"))
        jcenter()
    }
}

rootProject.name = "Felia"
include (":app")
include (":compose-ui")
include (
    ":wallet:core",
    ":wallet:assets",
    ":wallet:rates",
    ":wallet:ethereum",
    ":wallet:main",
)
include(":utils")

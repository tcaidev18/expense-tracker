fun findJdkWithJlink(): String? {
    val localProperties = java.util.Properties()
    val localPropertiesFile = file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
    }

    val candidates = listOfNotNull(
        System.getenv("ORG_GRADLE_JAVA_HOME"),
        localProperties.getProperty("org.gradle.java.home"),
        "/Applications/Android Studio.app/Contents/jbr/Contents/Home",
        "/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home",
        System.getenv("JAVA_HOME"),
    )

    return candidates.firstOrNull { candidate ->
        java.io.File(candidate, "bin/jlink").exists()
    }
}

findJdkWithJlink()?.let { jdkHome ->
    System.setProperty("org.gradle.java.home", jdkHome)
}

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ExpenseTrackerAndroid"
include(":app")

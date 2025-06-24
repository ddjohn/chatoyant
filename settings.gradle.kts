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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
        }
    }
}

rootProject.name = "chatoyant"
include(":chatoyant")
include(":chatoyant:crosscutting")
include(":chatoyant:mapbox")
include(":chatoyant:exoplayer")
include(":chatoyant:camera")
include(":chatoyant:home")
include(":chatoyant:settings")
include(":chatoyant:packages")

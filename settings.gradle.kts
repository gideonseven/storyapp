pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
   versionCatalogs {
        create("libs") {
            from(files("gradle/library.versions.toml"))
        }
    }
}
rootProject.name = "storyapp"
include (":app")
include(":core:image-loader")
include(":core:validation")

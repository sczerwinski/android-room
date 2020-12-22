pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "android-room"

include(":room:database")
include(":room:database-sql")

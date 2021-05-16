pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "android-room"

include(":room:converters")
include(":room:converters-processor")
include(":room:database")
include(":room:database-sql")
include(":room:extensions")

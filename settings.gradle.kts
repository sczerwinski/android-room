pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "android-room"

include(":room:converters")
include(":room:converters-processor")
include(":room:database")
include(":room:database-sql")

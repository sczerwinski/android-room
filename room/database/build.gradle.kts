plugins {
    id("com.android.library")
    kotlin("android")
    id("de.mannodermaus.android-junit5")
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
}

android {

    compileSdk = 33

    namespace = "it.czerwinski.android.room.database"

    defaultConfig {
        minSdk = 14
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.room:room-runtime:2.5.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
    testImplementation("io.mockk:mockk:1.13.5")
}

detekt {
    config = files("../../config/detekt/detekt.yml")
    buildUponDefaultConfig  = true
}

tasks {
    dokkaJavadoc { setUpJavadocTask(project) }
    dokkaJekyll { setUpJekyllTask(project) }

    artifacts {
        archives(createJavadocJar(dokkaJavadoc))
        archives(createSourcesJar(android.sourceSets.named("main").get().java.srcDirs))
    }
}

afterEvaluate {
    publishing {
        publications { registerAarPublication(project) }
        repositories { sonatype(project) }
    }
    signing { signAllMavenPublications(project, publishing) }
    tasks {
        getByName("generateMetadataFileForLibAarPublication")
            .dependsOn(getByName("sourcesJar"))
    }
}

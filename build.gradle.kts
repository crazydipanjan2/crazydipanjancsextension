import com.android.build.gradle.BaseExtension
import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.util.Properties

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4")
        classpath("com.github.recloudstream.gradle:gradle:81b1d424d")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

fun getSecret(key: String, fallback: String = ""): String {
    return fallback
}

fun Project.cloudstream(configuration: CloudstreamExtension.() -> Unit) = extensions.getByName<CloudstreamExtension>("cloudstream").configuration()

fun Project.android(configuration: BaseExtension.() -> Unit) = extensions.getByName<BaseExtension>("android").configuration()

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    cloudstream {
        setRepo(System.getenv("GITHUB_REPOSITORY") ?: "https://github.com/crazydipanjan2/crazydipanjancsextension.git")
        authors = listOf("crazydipanjan2")
    }

    android {
        namespace = "com.cncverse"

        defaultConfig {
            minSdk = 21
            compileSdkVersion(34)
            targetSdk = 34

            // Empty strings to prevent build failure on individual fork
            buildConfigField("String", "MOVIEBOX_SECRET_KEY_DEFAULT", "\"\"")
            buildConfigField("String", "MOVIEBOX_SECRET_KEY_ALT", "\"\"")
            buildConfigField("String", "CASTLE_SUFFIX", "\"\"")
            buildConfigField("String", "SIMKL_API", "\"\"")
            buildConfigField("String", "MAL_API", "\"\"")
            buildConfigField("String", "LIBRARY_PACKAGE_NAME", "\"com.cncverse\"")
            buildConfigField("String", "CRICIFY_PROVIDER_SECRET1", "\"\"")
            buildConfigField("String", "CRICIFY_PROVIDER_SECRET2", "\"\"")
            buildConfigField("String", "PIKASHOW_API_KEY", "\"\"")
            buildConfigField("String", "PIKASHOW_HMAC_SECRET", "\"\"")
            buildConfigField("String", "CRICFY_FIREBASE_API_KEY", "\"\"")
            buildConfigField("String", "CRICFY_FIREBASE_APP_ID", "\"\"")
            buildConfigField("String", "CRICFY_FIREBASE_PROJECT_NUMBER", "\"\"")
            buildConfigField("String", "SKLIVE_KEY", "\"\"")
            buildConfigField("String", "SKLIVE_IV", "\"\"")
            buildConfigField("String", "SKLIVE_V23_KEY", "\"\"")
            buildConfigField("String", "SKLIVE_V23_IV", "\"\"")
            buildConfigField("String", "SKTECH_FIREBASE_API_KEY", "\"\"")
            buildConfigField("String", "SKTECH_FIREBASE_APP_ID", "\"\"")
            buildConfigField("String", "SKTECH_FIREBASE_PROJECT_NUMBER", "\"\"")
            buildConfigField("String", "XON_FIREBASE_API_KEY", "\"\"")
            buildConfigField("String", "XON_FIREBASE_APP_ID", "\"\"")
            buildConfigField("String", "XON_FIREBASE_PROJECT_NUMBER", "\"\"")
            buildConfigField("String", "CINETV_SECRET_KEY_ENCRYPTED", "\"\"")
            buildConfigField("String", "CINETV_DES_KEY", "\"\"")
            buildConfigField("String", "CINETV_DES_IV", "\"\"")
            buildConfigField("String", "CINETV_AES_KEY", "\"\"")
            buildConfigField("String", "CINETV_AES_IV", "\"\"")
            buildConfigField("String", "CINETV_WS_SECRET", "\"\"")
            buildConfigField("String", "SMARTLINK_URL", "\"\"")
            buildConfigField("String", "SPEEDLINK_URL", "\"\"")
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<KotlinJvmCompile> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_1_8)
                freeCompilerArgs.addAll(
                    "-Xno-call-assertions",
                    "-Xno-param-assertions",
                    "-Xno-receiver-assertions",
                    "-Xannotation-default-target=param-property"
                )
            }
        }
    }

    dependencies {
        val implementation by configurations
        val cloudstream by configurations
        cloudstream("com.lagradost:cloudstream3:pre-release")

        implementation(kotlin("stdlib"))
        implementation("com.github.Blatzar:NiceHttp:0.4.11")
        implementation("org.jsoup:jsoup:1.16.1")
        implementation("androidx.annotation:annotation:1.7.0")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
        implementation("org.mozilla:rhino:1.7.14")
        implementation("me.xdrop:fuzzywuzzy:1.4.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        implementation("com.github.vidstige:jadb:v1.2.1")
        implementation("org.bouncycastle:bcpkix-jdk15on:1.70")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

import java.util.Properties
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
    id("com.google.devtools.ksp") version "2.0.21-1.0.28"
    id("jacoco")
}

// === Configuración dinámica de endpoints ===
val apiConfigDir = rootProject.file("config")
val apiConfigSampleFile = apiConfigDir.resolve("api-config.sample.properties")
val apiConfigFile = apiConfigDir.resolve("api-config.properties")

val apiConfigProps = Properties().apply {
    if (apiConfigSampleFile.exists()) {
        apiConfigSampleFile.inputStream().use { load(it) }
    }
    if (apiConfigFile.exists()) {
        apiConfigFile.inputStream().use { load(it) }
    }
}

fun normalizeUrl(value: String, ensureTrailingSlash: Boolean = true): String {
    val trimmed = value.trim()
    if (!ensureTrailingSlash) return trimmed
    return if (trimmed.endsWith("/")) trimmed else "$trimmed/"
}

fun configValue(key: String, defaultValue: String, ensureTrailingSlash: Boolean = false): String =
    normalizeUrl(
        value = apiConfigProps.getProperty(key)?.takeIf { it.isNotBlank() } ?: defaultValue,
        ensureTrailingSlash = ensureTrailingSlash
    )

fun String.asBuildConfigString(): String =
    "\"" + this.replace("\\", "\\\\").replace("\"", "\\\"") + "\""

val debugGatewayUrl = configValue("gateway.url.debug", "http://10.0.2.2:8094/", ensureTrailingSlash = true)
val deviceGatewayUrl = configValue("gateway.url.device", "http://192.168.1.100:8094/", ensureTrailingSlash = true)
val releaseGatewayUrl = configValue("gateway.url.release", "http://ec2-44-209-152-110.compute-1.amazonaws.com:8094/", ensureTrailingSlash = true)
val sharedApiKey = configValue("gateway.api.key", "levelup-2024-secret-api-key-change-in-production")
val mediaBaseUrl = configValue("media.base.url", "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/", ensureTrailingSlash = true)

// URLs específicas para el microservicio de carrito
val debugCarritoUrl = configValue("carrito.url.debug", "http://10.0.2.2:8008/", ensureTrailingSlash = true)
val deviceCarritoUrl = configValue("carrito.url.device", "http://192.168.1.100:8008/", ensureTrailingSlash = true)
val releaseCarritoUrl = configValue("carrito.url.release", releaseGatewayUrl, ensureTrailingSlash = true)

android {
    namespace = "com.example.levelupprueba"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.levelupprueba"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        // Configuración de firma para desarrollo (usa debug keystore)
        create("debugRelease") {
            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        
        // Para producción, crear un keystore real y configurarlo aquí
        // create("release") {
        //     storeFile = file("path/to/your/release.keystore")
        //     storePassword = "your-store-password"
        //     keyAlias = "your-key-alias"
        //     keyPassword = "your-key-password"
        // }
    }

    buildTypes {
        debug {
            // Configuración para desarrollo local
            buildConfigField("String", "API_BASE_URL", debugGatewayUrl.asBuildConfigString()) // Emulador / Docker
            buildConfigField("String", "API_BASE_URL_DEVICE", deviceGatewayUrl.asBuildConfigString())
            buildConfigField("String", "API_KEY", sharedApiKey.asBuildConfigString())
            buildConfigField("String", "MEDIA_BASE_URL", mediaBaseUrl.asBuildConfigString())
            buildConfigField("Boolean", "IS_PRODUCTION", "false")
            // URLs del carrito
            buildConfigField("String", "CARRITO_BASE_URL", debugCarritoUrl.asBuildConfigString())
            buildConfigField("String", "CARRITO_BASE_URL_DEVICE", deviceCarritoUrl.asBuildConfigString())
        }

        release {
            // Usar signing config de debug para desarrollo (cambiar por release en producción)
            signingConfig = signingConfigs.getByName("debugRelease")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Configuración para producción AWS
            buildConfigField("String", "API_BASE_URL", releaseGatewayUrl.asBuildConfigString())
            buildConfigField("String", "API_BASE_URL_DEVICE", releaseGatewayUrl.asBuildConfigString())
            buildConfigField("String", "API_KEY", sharedApiKey.asBuildConfigString())
            buildConfigField("String", "MEDIA_BASE_URL", mediaBaseUrl.asBuildConfigString())
            buildConfigField("Boolean", "IS_PRODUCTION", "true")
            // URLs del carrito
            buildConfigField("String", "CARRITO_BASE_URL", releaseCarritoUrl.asBuildConfigString())
            buildConfigField("String", "CARRITO_BASE_URL_DEVICE", releaseCarritoUrl.asBuildConfigString())
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true // Habilitar BuildConfig para acceder a las constantes
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all {
                it.useJUnitPlatform()  // JUnit 5
                it.jvmArgs("-XX:+EnableDynamicAgentLoading")
            }
        }
    }
}

dependencies {
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.androidx.ui.test.junit4)
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("androidx.compose.animation:animation:1.5.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-rc02")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    testImplementation(libs.junit)
    testImplementation(libs.junitJupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.mockk)                  // MockK
    testImplementation(libs.junitJupiter)           // JUnit5 (junit-jupiter)
    testImplementation(libs.kotlinxCoroutinesTest)  // kotlinx-coroutines-test
    testImplementation(libs.kotestRunnerJunit5)     // Kotest runner
    testImplementation(libs.kotestAssertionsCore)   // Kotest assertions
    testImplementation("ch.qos.logback:logback-classic:1.5.6")


    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ViewModel + Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Retrofit - Cliente HTTP para APIs
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Coil - Carga de imágenes
    implementation("io.coil-kt:coil-compose:2.5.0")

    // OSMDroid - Mapa OpenStreetMap (sin API key) ya q no se puede con JS como el otro tengo q hacer una cosa rara 
    implementation("org.osmdroid:osmdroid-android:6.1.18")
    testImplementation(kotlin("test"))

}

// Configuración de JaCoCo para cobertura de tests
tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

tasks.register("jacocoTestReport", JacocoReport::class) {
    dependsOn("testDebugUnitTest")
    
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
    
    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/*_Hilt*.*",
        "**/*Module*.*",
        "**/*Component*.*"
    )
    
    val debugTree = fileTree("${project.buildDir}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }
    val mainSrc = "${project.projectDir}/src/main/java"
    
    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree("${project.buildDir}/jacoco") {
        include("testDebugUnitTest.exec")
    })
}
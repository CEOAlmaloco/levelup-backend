plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
    id("com.google.devtools.ksp") version "2.0.21-1.0.28"
}

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

    buildTypes {
        debug {
            // Configuración para desarrollo local
            buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8094/\"") // Emulador Android
            buildConfigField("String", "API_KEY", "\"levelup-2024-secret-api-key-change-in-production\"")
            buildConfigField("Boolean", "IS_PRODUCTION", "false")
        }
        
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Configuración para producción AWS
            // IMPORTANTE: Cambiar esta URL después de desplegar en AWS
            // Ejemplo: "https://api.levelup-tu-dominio.com/" o "https://tu-dominio-aws.amazonaws.com:8094/"
            buildConfigField("String", "API_BASE_URL", "\"https://api-gateway.tu-dominio.com/\"") // AWS Producción
            buildConfigField("String", "API_KEY", "\"levelup-2024-secret-api-key-change-in-production\"")
            buildConfigField("Boolean", "IS_PRODUCTION", "true")
        }
    }
    
    buildFeatures {
        compose = true
        buildConfig = true // Habilitar BuildConfig para acceder a las constantes
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("androidx.room:room-runtime:2.6.1")
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
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

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
}
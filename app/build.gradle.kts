
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "com.diego.matesanz.arcaneum"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.diego.matesanz.arcaneum"
        minSdk = 28
        targetSdk = 35
        versionCode = 4
        versionName = "0.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Modules
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":usecases"))
    implementation(project(":framework"))

    // Kotlin core
    implementation(libs.androidx.core.ktx)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Jetpack Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Jetpack Compose: Navigation
    implementation(libs.androidx.navigation.compose)

    // Jetpack Compose: Coil
    implementation(libs.coil.compose)

    // Material
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material)

    // Zxing
    implementation(libs.zxing.android.embedded)
    implementation(libs.zxing.core)

    // Palette
    implementation (libs.androidx.palette)

    // Room
    implementation(libs.androidx.room.ktx)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

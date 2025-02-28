import java.util.Properties

plugins {
    id("arcaneum.android.library")
    id("arcaneum.di.library")
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.diego.matesanz.arcaneum.framework"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").readText().byteInputStream())

        val googleBooksApiKey = properties.getProperty("GOOGLE_BOOKS_API_KEY", "")
        buildConfigField("String", "GOOGLE_BOOKS_API_KEY", "\"$googleBooksApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // Modules
    implementation(project(":domain"))
    implementation(project(":data"))

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)

    // Okhttp3
    implementation(libs.okhttp)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Gson
    implementation(libs.gson)

    // Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}

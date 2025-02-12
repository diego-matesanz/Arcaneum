
plugins {
    id("arcaneum.jvm.library")
}
dependencies {
    // Modules
    implementation(project(":data"))
    implementation(project(":domain"))

    // Kotlin coroutines
    implementation(libs.kotlinx.coroutines.android)
}

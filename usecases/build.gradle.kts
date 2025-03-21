
plugins {
    id("arcaneum.jvm.library")
    id("arcaneum.di.library")
}
dependencies {
    // Modules
    implementation(project(":data"))
    implementation(project(":domain"))

    // Kotlin coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Test
    testImplementation(project(":test:unit"))
}

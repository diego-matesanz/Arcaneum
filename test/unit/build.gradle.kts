
plugins {
    id("arcaneum.jvm.library")
}

dependencies {
    // Modules
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.junit)
    implementation(libs.kotlinx.coroutines.test)
}

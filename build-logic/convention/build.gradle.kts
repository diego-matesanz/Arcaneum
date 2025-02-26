
plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "arcaneum.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "arcaneum.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("jvmLibrary") {
            id = "arcaneum.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("diLibrary") {
            id = "arcaneum.di.library"
            implementationClass = "DiLibraryConventionPlugin"
        }
        register("diComposeLibrary") {
            id = "arcaneum.di.compose.library"
            implementationClass = "DiComposeLibraryConventionPlugin"
        }
    }
}

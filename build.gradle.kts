
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.arturbosch.detekt)
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}

val detektFormatting = libs.detekt.formatting

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))

        dependencies {
            detektPlugins(detektFormatting)
        }
    }
}

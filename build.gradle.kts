
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.arturbosch.detekt)
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))

        dependencies {
            detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")
        }
    }
}

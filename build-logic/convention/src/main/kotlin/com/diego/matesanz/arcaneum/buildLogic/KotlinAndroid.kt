package com.diego.matesanz.arcaneum.buildLogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        compileSdk = 35

        defaultConfig {
            minSdk = 28
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        tasks.withType<KotlinCompile> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }

        tasks.withType<Test> {
            useJUnitPlatform()
        }

        dependencies {
            add("implementation", libs.findLibrary("androidx.core.ktx").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())

            add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
            add("testImplementation", libs.findLibrary("turbine").get())

            add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
        }

        addUnitTestDependencies()
    }
}

internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    addUnitTestDependencies()
}

private fun Project.addUnitTestDependencies() {
    dependencies {
        add("testImplementation", libs.findLibrary("junit").get())
        add("testImplementation", libs.findLibrary("junit.vintage").get())
        add("testImplementation", libs.findLibrary("mockito.kotlin").get())
    }
}

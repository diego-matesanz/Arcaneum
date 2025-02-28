import com.diego.matesanz.arcaneum.buildLogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DiComposeLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("arcaneum.di.library")
                apply("com.google.dagger.hilt.android")
            }

            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
            }
        }
    }
}

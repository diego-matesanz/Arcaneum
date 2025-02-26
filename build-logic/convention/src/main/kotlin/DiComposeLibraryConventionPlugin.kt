import com.diego.matesanz.arcaneum.buildLogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DiComposeLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("arcaneum.di.library")

            dependencies {
                add("implementation", libs.findLibrary("koin.compose").get())
            }
        }
    }
}

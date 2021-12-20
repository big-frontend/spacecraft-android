dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "gradle-plugin"
includeBuild("groovy-gradle-plugin/")
include(
    ":app",
    ":bundle1",
    ":bundle2",
)

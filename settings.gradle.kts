pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "jithin-monorepo"

include("eapen-bookrecords:domain")
include("eapen-bookrecords:application")
include("eapen-bookrecords:infrastructure")
include("eapen-bookrecords:ui")

dependencies {
    implementation(project(":eapen-bookrecords:domain"))

    // SQLite and Exposed
    implementation("org.jetbrains.exposed:exposed-core:0.45.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.45.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.45.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.45.0")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
}

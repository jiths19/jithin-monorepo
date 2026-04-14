plugins {
    application
}

application {
    mainClass.set("com.eapen.bookrecords.application.ApplicationKt")
}

dependencies {
    implementation(project(":eapen-bookrecords:domain"))
    implementation(project(":eapen-bookrecords:infrastructure"))
    implementation(project(":eapen-bookrecords:ui"))

    implementation("io.ktor:ktor-server-core:2.3.8")
    implementation("io.ktor:ktor-server-netty:2.3.8")
    implementation("io.ktor:ktor-server-html-builder:2.3.8")
    implementation("io.ktor:ktor-server-auth:2.3.8")
    implementation("io.ktor:ktor-server-sessions:2.3.8")

    implementation("org.jetbrains.exposed:exposed-core:0.45.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.45.0")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    implementation("ch.qos.logback:logback-classic:1.4.14")
}

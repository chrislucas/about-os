plugins {
    kotlin("jvm") version "1.6.21"
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

/*
    https://ppbruna.medium.com/demystifying-gradle-b827f1bc4553
    https://medium.com/collabcode/come%C3%A7ando-com-o-gradle-4e96fd12d933

 */

dependencies {
    /*
        Diferencas entre
        implementation, compile, runtimeOnly, testCompile, testImplmentation, testCompileOnly
        https://ppbruna.medium.com/demystifying-gradle-b827f1bc4553
        diferencas  entre compile e implementation
     */
    implementation(kotlin("stdlib"))
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    /*
        https://klogging.io/docs/get-started
        https://www.baeldung.com/kotlin/logging
        https://www.baeldung.com/kotlin/kotlin-logging-library
     */
    implementation("io.klogging:klogging-jvm:0.4.9")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
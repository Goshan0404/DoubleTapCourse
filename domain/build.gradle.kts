plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}



dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation ("com.google.dagger:dagger:2.51")
    kapt ("com.google.dagger:dagger-compiler:2.48")

}
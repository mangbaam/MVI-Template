// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0-beta01" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    kotlin("kapt") version "1.8.21"
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
}

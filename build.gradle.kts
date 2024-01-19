plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.firebaseCraslytics) apply false
}

buildscript {
    dependencies {
        classpath(libs.org.jacoco.core)
    }
}

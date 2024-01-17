plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    kotlin("plugin.serialization") version "1.9.21"
    id("jacoco")
}

android {
    namespace = "com.rodrigolmti.lunch.money.companion"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rodrigolmti.lunch.money.companion"
        minSdk = 26
        targetSdk = 34
        versionCode = 202401151
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures.buildConfig = true

    buildTypes {
        debug {
            enableUnitTestCoverage = true
            isShrinkResources = false
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

jacoco {
    toolVersion = "0.8.7"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.material3)
    implementation(libs.material)

    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.serialization)
    implementation(libs.kotlinx.serialization)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    testImplementation(libs.kotlin.test.common)
    testImplementation(libs.kotlin.test.annotations)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit)
    testImplementation(libs.androidx.test.runner)
    testImplementation(libs.androidx.test.rules)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
}
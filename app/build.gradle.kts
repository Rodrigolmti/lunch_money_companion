plugins {
    alias(libs.plugins.companion.android.application)
    alias(libs.plugins.companion.android.application.jacoco)
    kotlin("plugin.serialization") version libs.versions.kotlin
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    id("jacoco")
}

android {
    namespace = "com.rodrigolmti.lunch.money.companion"

    defaultConfig {
        applicationId = "com.rodrigolmti.lunch.money.companion"
        versionCode = 340000004
        versionName = "1.6.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures.buildConfig = true

    signingConfigs {
        create("release") {
            storeFile = file("../keys/release.jks")
            keyAlias = System.getenv("ANDROID_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
            storePassword = System.getenv("SIGNING_STORE_PASSWORD")
        }
    }

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
            signingConfig = signingConfigs.getByName("release")
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
    composeCompiler {
        enableStrongSkippingMode = true
        includeSourceInformation = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.google.material)
    implementation(libs.coil)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

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

    implementation(libs.mp.android.chart)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    testImplementation(libs.kotlin.test.common)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test.annotations)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit)
    testImplementation(libs.androidx.test.runner)
    testImplementation(libs.androidx.test.rules)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
}
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)

//    alias(libs.plugins.jetbrains.kotlin.android)
//    id("com.google.devtools.ksp")
//    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.filip.cryptoViewer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.filip.cryptoViewer"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Load API_URL from local.properties
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")

        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
        }

        val apiUrl = localProperties.getProperty("API_URL", "")
        buildConfigField("String", "API_URL", "\"$apiUrl\"")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
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

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.9.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(libs.androidx.junit.ktx)
    implementation(libs.common)
    implementation(libs.generativeai)
    //hilt
    ksp (libs.hilt.compiler.ksp)
    implementation (libs.hilt.android)
    implementation (libs.hilt.navigation.compose)
    implementation (libs.hilt.common)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler.v250)
    implementation(libs.androidx.room.ktx)


    //mockito
    implementation(libs.mockito.core.v5142)
    testImplementation (libs.mockito.kotlin.v510)
    testImplementation (libs.mockito.inline)



    // AndroidX Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Compose dependencies
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.flowlayout)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx.v231)


    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Testing dependencies
    testImplementation(libs.truth)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)

    // Debugging and Testing
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

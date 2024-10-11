import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")

}

android {
    namespace = "com.Filip.cryptoviewer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.Filip.cryptoviewer"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")

        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
        }

        // Get API_URL from local.properties, default to an empty string if not found
        val apiUrl = localProperties.getProperty("API_URL", "")

        // Add it to BuildConfig
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
    buildFeatures {
        compose = true
        buildConfig = true
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

dependencies {
    kapt (libs.hilt.compiler.v248)

    // Unit Testing
   // testImplementation (libs.junit)
    implementation (libs.mockito.android.v5130)

    testImplementation (libs.mockito.inline) // use the latest version

    testImplementation (libs.mockito.core)
    testImplementation (libs.androidx.core.testing)
    testImplementation (libs.truth)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.mockk)
    testImplementation (libs.mockito.core.v400)
    testImplementation (libs.mockito.kotlin) // For Kotlin-specific extensions
    testImplementation (libs.junit.v412)
    // required if you want to use Mockito for Android tests
    androidTestImplementation (libs.mockito.android)




    // Android Instrumentation Testing
    androidTestImplementation (libs.junit.v113)
    androidTestImplementation (libs.androidx.espresso.core.v340)
    androidTestImplementation (libs.ui.test.junit4)
    androidTestImplementation (libs.androidx.room.testing)
    androidTestImplementation (libs.kotlinx.coroutines.test)

    // For testing (androidTest)
    androidTestImplementation (libs.hilt.android.testing)
    kaptAndroidTest (libs.hilt.compiler)
    kaptAndroidTest (libs.hilt.android.compiler)
    androidTestImplementation (libs.okhttp3.mockwebserver)



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation (libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Compose dependencies
    implementation( libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.accompanist.flowlayout)


    // Coroutines
    implementation( libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    // Coroutine Lifecycle Scopes
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx.v231)

    //room
    implementation (libs.androidx.room.runtime)
    //kapt (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    //hilt
    implementation(libs.hilt.android.v248)
    kapt(libs.hilt.android.compiler.v248)
    kapt (libs.androidx.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)

    //chart
    implementation (libs.charts)
    implementation(libs.compose.charts)

    implementation (libs.androidx.runtime.livedata)


    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation( libs.okhttp)
    implementation (libs.logging.interceptor)




    //room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt (libs.androidx.room.compiler) // Use kapt for Kotlin



}

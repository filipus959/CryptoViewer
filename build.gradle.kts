

plugins {
    // git
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false

}


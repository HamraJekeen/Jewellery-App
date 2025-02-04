

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.euphoria_colombo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.euphoria_colombo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.core:core-ktx:1.10.1")
    // Material Icons (from Material 2)
    implementation ("androidx.compose.material:material-icons-extended:1.7.2")
    implementation ("com.google.accompanist:accompanist-pager:0.30.0")


    // Material 3
    implementation ("androidx.compose.material3:material3:1.3.0")
    implementation ("androidx.navigation:navigation-compose:2.8.1")
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.30.0")
// or latest version
    implementation ("androidx.compose.animation:animation:1.7.2")

    implementation ("com.google.accompanist:accompanist-navigation-animation:0.30.0")
    implementation ("androidx.compose.ui:ui-tooling:1.7.2")
    implementation ("androidx.compose.ui:ui:1.7.2")
    implementation ("androidx.compose.foundation:foundation:1.7.2")
    implementation ("androidx.compose.material:material:1.7.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation ("io.coil-kt:coil-compose:2.3.0")  // Coil for Jetpack Compose
    implementation ("androidx.activity:activity-compose:1.6.0")  // Activity Result API
    implementation ("androidx.core:core-ktx:1.10.0")  // FileProvider support
    // Optional: CameraX dependencies for a more customizable camera
    implementation ("androidx.camera:camera-camera2:1.1.0")
    implementation ("androidx.camera:camera-lifecycle:1.1.0")

    //implementation ("androidx.camera:camera-core:1.5.0")
    //implementation ("androidx.camera:camera-camera2:1.5.0")
    //implementation ("androidx.camera:camera-lifecycle:1.5.0")
    //implementation ("androidx.camera:camera-view:1.5.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.code.gson:gson:2.10.1")

















}
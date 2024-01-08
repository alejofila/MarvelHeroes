plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.example.marvelchallenge"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.marvelchallenge"
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
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    kotlinCompilerExtensionVersion = "1.4.3"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  val composeBom = platform(libs.compose.bom)

  implementation(libs.core.ktx)
  implementation(libs.lifecycle.runtime.ktx)
  implementation(libs.activity.compose)
  implementation(libs.coil)
  implementation(libs.coil.compose)
  implementation(composeBom)
  implementation(libs.compose.ui)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.tooling.preview)
  implementation(libs.compose.material3)
  implementation(libs.lifecycle.viewmodel.compose)
  implementation(libs.lifecycle.viewmodel.ktx)
  implementation(libs.retrofit)
  implementation(libs.moshi.kotlin)
  implementation(libs.moshi.converter)
  implementation (libs.okhttp.logging.interceptor)
  kapt(libs.dagger.compiler)
  implementation(libs.dagger)
  implementation(libs.hilt.android)
  implementation(libs.compose.navigation)
  kapt(libs.hilt.android.compiler)
  implementation (libs.navigation.fragment.ktx)
  implementation (libs.navigation.ui.ktx)
  testImplementation(libs.junit)
  androidTestImplementation(libs.test.ext.junit)
  androidTestImplementation(libs.espresso.core)
  androidTestImplementation(composeBom)
  androidTestImplementation(libs.compose.ui.test.junit4)
  debugImplementation(libs.compose.ui.tooling)
  debugImplementation(libs.compose.ui.test.manifest)
}

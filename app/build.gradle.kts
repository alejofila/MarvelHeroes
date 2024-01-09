import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

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
    val key: String = gradleLocalProperties(rootDir).getProperty("MARVEL_API_KEY") ?: ""
    buildConfigField("String", "MARVEL_API_KEY", "\"$key\"")

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
    buildConfig = true
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
  implementation(composeBom)
  implementation(libs.activity.compose)
  implementation(libs.coil)
  implementation(libs.coil.compose)
  implementation(libs.compose.material3)
  implementation(libs.compose.navigation)
  implementation(libs.compose.ui)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.tooling.preview)
  implementation(libs.core.ktx)
  implementation(libs.dagger)
  implementation(libs.hilt.android)
  implementation(libs.lifecycle.runtime.ktx)
  implementation(libs.lifecycle.viewmodel.compose)
  implementation(libs.lifecycle.viewmodel.ktx)
  implementation(libs.mockito.kotlin)
  implementation(libs.moshi.converter)
  implementation(libs.moshi.kotlin)
  implementation(libs.navigation.fragment.ktx)
  implementation(libs.navigation.ui.ktx)
  implementation(libs.okhttp.logging.interceptor)
  implementation(libs.retrofit)
  testImplementation(libs.junit)
  testImplementation(libs.coroutines.test)
  androidTestImplementation(composeBom)
  androidTestImplementation(libs.espresso.core)
  androidTestImplementation(libs.test.ext.junit)
  androidTestImplementation(libs.compose.ui.test.junit4)
  debugImplementation(libs.compose.ui.test.manifest)
  debugImplementation(libs.compose.ui.tooling)
  kapt(libs.dagger.compiler)
  kapt(libs.hilt.android.compiler)

}

fun getProperty(propertyName: String): String {
  val properties = Properties()
  val localPropertiesFile = File(rootProject.projectDir, "local.properties")

  if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { properties.load(it) }
  }

  return properties.getProperty(propertyName, "")
}

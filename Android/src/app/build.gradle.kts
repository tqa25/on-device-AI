/*
 * Copyright 2025 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.protobuf.gradle.* // <-- cần cho GenerateProtoTask, builtins, named, option

plugins {
  alias(libs.plugins.android.application)
  // Note: set apply to true to enable google-services (requires google-services.json).
  alias(libs.plugins.google.services) apply false
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.protobuf)
  alias(libs.plugins.hilt.application)
  alias(libs.plugins.oss.licenses)
  kotlin("kapt")
}

android {
  namespace = "com.google.ai.edge.gallery"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.google.aiedge.gallery"
    minSdk = 31
    targetSdk = 35
    versionCode = 13
    versionName = "1.0.7"

    // Needed for HuggingFace auth workflows.
    // Use the scheme of the "Redirect URLs" in HuggingFace app.
    manifestPlaceholders["appAuthRedirectScheme"] =
      "REPLACE_WITH_YOUR_REDIRECT_SCHEME_IN_HUGGINGFACE_APP"
    manifestPlaceholders["applicationName"] = "com.google.ai.edge.gallery.GalleryApplication"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("debug")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  // Kotlin compiler options
  kotlinOptions {
    jvmTarget = "17"
    freeCompilerArgs += "-Xcontext-receivers"
  }

  buildFeatures {
    compose = true
    buildConfig = true
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
  implementation(libs.androidx.compose.navigation)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.material.icon.extended)
  implementation(libs.androidx.work.runtime)
  implementation(libs.androidx.datastore)
  implementation(libs.com.google.code.gson)
  implementation(libs.androidx.lifecycle.process)
  implementation(libs.androidx.security.crypto)

  implementation(libs.litertlm)
  implementation(libs.commonmark)
  implementation(libs.richtext)

  implementation(libs.tflite)
  implementation(libs.tflite.gpu)
  implementation(libs.tflite.support)

  implementation(libs.camerax.core)
  implementation(libs.camerax.camera2)
  implementation(libs.camerax.lifecycle)
  implementation(libs.camerax.view)

  implementation(libs.openid.appauth)
  implementation(libs.androidx.splashscreen)

  // Protobuf lite runtime
  implementation(libs.protobuf.javalite)

  // Hilt
  implementation(libs.hilt.android)
  implementation(libs.hilt.navigation.compose)
  kapt(libs.hilt.android.compiler)

  implementation(libs.play.services.oss.licenses)
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.analytics)

  implementation(libs.androidx.exifinterface)

  // Room persistence library for chat logs
  implementation("androidx.room:room-runtime:2.6.1")
  implementation("androidx.room:room-ktx:2.6.1")
  kapt("androidx.room:room-compiler:2.6.1")

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  androidTestImplementation(libs.hilt.android.testing)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}

// Protobuf plugin configuration (Kotlin DSL chuẩn + bật "lite")
protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc:4.26.1"
  }
  generateProtoTasks {
    all().forEach { task: com.google.protobuf.gradle.GenerateProtoTask ->
      task.builtins {
        // Java codegen + lite runtime
        named("java") {
          option("lite")
        }
      }
    }
  }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("../etc/keystore.jks")
            storePassword = "android"
            keyAlias = "platform"
            keyPassword = "android"
        }
    }
    namespace = "com.avelon.chatoyant"
    compileSdk = 35

    buildFeatures {
        compose = true
    }

    defaultConfig {
        applicationId = "com.avelon.chatoyant"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "../libs", "include" to listOf("*.jar"))))

    implementation(project(":chatoyant:home"))
    implementation(project(":chatoyant:packages"))
    implementation(project(":chatoyant:settings"))

    implementation(project(":chatoyant:camera"))
    implementation(project(":chatoyant:exoplayer"))
    implementation(project(":chatoyant:mapbox"))
    implementation(project(":chatoyant:crosscutting"))

    // Media
    implementation(libs.androidx.media)
    implementation(libs.androidx.media3.session)

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2025.02.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.viewbinding)

    // Gauges
    // implementation("pl.pawelkleczkowski.customgauge:CustomGauge:1.0.4")

    // To be tested
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Template
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.ui.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

dependencies {
}

dependencies {
    implementation(libs.androidx.activity)
    implementation("androidx.cardview:cardview:1.0.0")
}

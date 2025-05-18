import com.android.build.api.variant.BuildConfigField

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    kotlin("kapt")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
    kotlin("plugin.serialization") version "1.9.23"
}

android {
    namespace = "ua.nure.estateflow"
    compileSdk = 35

    defaultConfig {
        applicationId = "ua.nure.estateflow"
        minSdk = 33
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
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

androidComponents {
    onVariants {
        it.buildConfigFields?.put(
            "CFG_SERVER_URL",
//            BuildConfigField(type  = "String",value = "\"http:192.168.50.79:8080\"", comment = "server config url")
            BuildConfigField(type  = "String",value = "\"http://192.168.50.124:8080\"", comment = "server config url")
        )
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
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)

    //DI
    implementation(libs.android.hilt)
    ksp(libs.android.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //Network
    implementation(libs.retrofit)
    implementation(libs.retrofitConvertorGSon)
    implementation(libs.chucker)
//    implementation(libs.chuckerNoOp)

    //DB
    implementation(libs.roomCommon)
    implementation(libs.roomRuntime)
    implementation(libs.roomKtx)
    implementation(libs.roomPaging)
    ksp(libs.roomCompiler)

    //    AsyncImage
    implementation(libs.coil.compose)
    implementation(libs.coil.network)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.safe.args)

}

android {
    signingConfigs {
        create("appSigningConfig") {
            val signingProps = Properties().also {
                it.load(file("../signingInfo/SigningInfo.properties").inputStream())
            }

            storeFile = signingProps["FilePath"]?.let { file(it) }
            storePassword = signingProps["StorePassword"] as String?
            keyAlias = signingProps["KeyAlias"] as String?
            keyPassword = signingProps["KeyPassword"] as String?
        }
    }

    namespace = "com.shaadi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.shaadi"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("appSigningConfig")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)


    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)


    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Timber
    implementation(libs.timber)

    //Glide
    implementation(libs.glide)

    //Pagination
    implementation (libs.androidx.paging.runtime.ktx)


    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)
    ksp (libs.androidx.room.compiler)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
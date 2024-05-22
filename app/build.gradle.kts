plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.project.panacea"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.panacea"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
//    packagingOptions {
//        exclude ("mockito-extensions/org.mockito.plugins.MockMaker")
//    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    //implementation(libs.firebase.inappmessaging.display)
    implementation(libs.lottie)
    implementation(libs.volley)
    implementation(libs.glide)
    implementation(libs.okhttp)
    implementation(libs.circleimageview)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Add these if not already present
    testImplementation(libs.junit)
    testImplementation(libs.robolectric.robolectric)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.mockito.core)
    //testImplementation(libs.mockito.android)
    testImplementation(libs.mockito.inline)


//    androidTestImplementation(libs.robolectric.robolectric)
//    androidTestImplementation(libs.mockito.core.v3112)
    androidTestImplementation(libs.mockito.android)
//    androidTestImplementation(libs.powermock.module.junit4)
//    androidTestImplementation(libs.powermock.api.mockito2)
//    androidTestImplementation(libs.mockito.inline)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.remote)
    androidTestImplementation(libs.androidx.core)
    implementation(libs.volley)
    androidTestImplementation(libs.androidx.espresso.intents)
}

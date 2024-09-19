plugins {
    alias(libs.plugins.android.application)
    kotlin("kapt")
    id ("org.jetbrains.kotlin.android")
    id("org.ajoberstar.grgit' 4.1.0")
}

android {
    namespace = "com.example.mvvm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mvvm"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildFeatures{
            viewBinding = true
        }

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:2.6.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation ("androidx.room:room-ktx:2.5.0")    // Room with Coroutines support
    implementation ("androidx.multidex:multidex:2.0.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

}
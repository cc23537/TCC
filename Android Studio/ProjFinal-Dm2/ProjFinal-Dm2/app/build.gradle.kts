plugins {
    alias(libs.plugins.androidApplication)

    id("com.google.gms.google-services")
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.android")

}


android {
    namespace = "com.example.loginfrag"
    compileSdk = 34

    viewBinding {
        enable = true
    }

    buildFeatures{
        buildConfig = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.loginfrag"
        minSdk = 24
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.auth)
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-database-ktx:latest_version")
    implementation(libs.firebase.database)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("com.google.code.gson:gson:2.8.2")
    //implementation("com.squareup.okhttp3:3.10.0")
    //implementation("org.jetbrains.anko:anko-sqlite:.10.4")
    implementation("com.squareup.retrofit2:retrofit:2.7.2")
    //implementation("com.squareup.retrofit2:convert-gson:2.7.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.1")

}
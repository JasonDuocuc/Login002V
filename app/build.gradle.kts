plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)


// Agregar esto para habilitar KAPT

    kotlin("kapt")


}

android {
    namespace = "com.example.login002v"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.login002v"
        minSdk = 24
        targetSdk = 36
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


    // Dependencia para la navegación con Jetpack Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")


// Íconos y EXTENDIDOS
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")


// Dependencias Room
    implementation("androidx.room:room-runtime:2.6.1")  // Versión actualizada
    kapt("androidx.room:room-compiler:2.6.1")          // Misma versión
    implementation("androidx.room:room-ktx:2.6.1")     // Misma versión



    // Para conectarnos a Internet (APIs y Spring Boot)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Para pruebas unitarias, Verifica si ya las tienes, si no, las agrega
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.3.1")
    // -------------------




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
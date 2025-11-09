plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.safe.args.kotlin)
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.0"
}



android {
    namespace  = "com.mod6.ae2_abpro1_listeylor"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mod6.ae2_abpro1_listeylor"
        minSdk        = 29
        targetSdk     = 36
        versionCode   = 1
        versionName   = "1.0"

        // Usamos el Runner estándar, ya que eliminamos el CustomTestRunner
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


    }

    // ELIMINADO: Se eliminó el bloque sourceSets/jniLibs/resources, ya que
    //            no se requiere para Mockito o Robolectric.
    //            (Estaba causando problemas de configuración inútiles).

    buildFeatures {
        viewBinding = true
        dataBinding = false
    }

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/*.txt"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

    // ELIMINADO: Se eliminó el bloque testOptions{} con jvmArgs, ya que era para MockK/JVMTI.

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
}

allOpen {
    // Aplica el comportamiento 'open' a todas las clases anotadas con:
    annotation("com.mod6.ae2_abpro1_listeylor.testing.OpenClass")
    // O si quieres ser más general, aplicar a @ViewModel (aunque no existe nativamente)
    // Opcionalmente, se puede aplicar a clases con @Mockable si tienes una anotación personalizada.
}

configurations.all {
    resolutionStrategy {
        force(
            libs.androidx.test.core,
            libs.androidx.test.ext.junit,
            libs.androidx.espresso.core,
            libs.androidx.fragment.testing,
            libs.androidx.navigation.testing
        )
    }
}

dependencies {
    // --- IMPLEMENTATION (Consistente con libs) ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.glide)

    // -----------------------------------------------------------------------
    // TESTING
    // -----------------------------------------------------------------------

    // --- Unit Tests ---
    testImplementation(libs.junit)
    testImplementation(libs.arch.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)

// --- Instrumented Tests ---
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.navigation.testing)

// Fragment Testing solo en debug
    debugImplementation(libs.androidx.fragment.testing)
}
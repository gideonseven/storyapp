plugins {
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.android.application")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.don.storyApp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner ="androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        buildConfigField("String", "PREFERENCES_NAME", "\"story_app_pref_file\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        dataBinding = true
    }

    testOptions {
        animationsDisabled = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation (libs.androidx.appcompat)
    implementation (libs.android.material)

    // RecyclerView
    implementation (libs.androidx.recyclerview)

    // androidx constraintLayout
    implementation (libs.androidx.constraintlayout)

    // androidx navigation
    implementation (libs.androidx.navigation)
    implementation (libs.androidx.navigation.ui)

    // androidx livedata
    implementation (libs.androidx.lifecycle.livedata.ktx)

    // retrofit & okhttp
    implementation (libs.retrofit.core)
    implementation (libs.retrofit.gson)
    implementation (libs.okhttp.core)

    // dagger-hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)

    //hilt - test - with kotlin
    kaptTest (libs.hilt.compiler)
    // For instrumented tests.
    androidTestImplementation (libs.hilt.android.testing)
    // ...with Kotlin.
    kaptAndroidTest (libs.hilt.compiler)

    // Timber
    implementation (libs.timber)

    // sandwich
    implementation (libs.sandwitch)

    // lottie
    implementation (libs.lottie)

    // glide
    implementation (libs.glide.core)
    kapt (libs.glide.compiler)

    //camera x
    implementation (libs.androidx.camera.core)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.view)

    // easy permission
    implementation (libs.easyPermissions)

    // paging 3
    implementation (libs.androidx.paging3.runtime)

    //maps
    implementation (libs.android.maps)

    // location
    implementation (libs.android.location)

    // room
    implementation (libs.room.ktx)
    implementation  (libs.room.paging)
    kapt (libs.room.compiler)

    // niddler
    implementation (libs.niddler.base)

    //testing
    testImplementation (libs.junit4)
    androidTestImplementation (libs.androidx.test.ext)
    androidTestImplementation (libs.androidx.test.espresso.core)

    //mockito
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.inline)

    //special testing
    testImplementation (libs.androidx.arch.core) // InstantTaskExecutorRule
    testImplementation (libs.jetbrains.kotlinx.coroutine.test)
    //TestCoroutineDispatcher

    //special instrumentation testing
    androidTestImplementation (libs.androidx.arch.core) // InstantTaskExecutorRule
    androidTestImplementation (libs.jetbrains.kotlinx.coroutine.test)
    //TestCoroutineDispatcher
    debugImplementation (libs.androidx.fragment.testing) //launchFragmentInContainer
    androidTestImplementation (libs.android.support.test.espresso)
    //RecyclerViewActions

    //coroutine support
    implementation (libs.androidx.lifecycle.viewmodel.ktx) //viewModelScope

    //TestCoroutineDispatcher
    debugImplementation (libs.androidx.fragment.testing) //launchFragmentInContainer

    //mock web server
    androidTestImplementation (libs.okhttp.mockwebserver)
    androidTestImplementation (libs.okhttp.tls)
    implementation (libs.androidx.test.espresso.idling.resource)

    androidTestImplementation (libs.androidx.test.espresso.intent)//IntentsTestRule
}
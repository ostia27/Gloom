@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.moko.resources)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

android {
    namespace = "com.materiiapps.gloom.ui"

    defaultConfig {
        compileSdk = 34
        minSdk = 21
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

kotlin {
    android()
    jvmToolchain(17)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":api"))
                implementation(project(":shared"))

                api(libs.bundles.kotlinx)
                api(libs.bundles.voyager)

                api(compose.material3)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(compose.runtime)

                api(libs.accompanist.flowlayout)
                api(libs.accompanist.pager)
                api(libs.androidx.paging.compose)
                api(libs.compose.imageloader)
                api(libs.koin.core)
                api(libs.koin.androidx.compose)
                api(libs.moko.resources.compose)
                api(libs.multiplatform.paging)
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                api(libs.accompanist.systemuicontroller)
                api(libs.accompanist.webview)
                api(libs.androidx.core.ktx)
                api(libs.koin.android)
            }
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.materiiapps.gloom"
    multiplatformResourcesClassName = "Res"
    iosBaseLocalizationRegion = "en"
}
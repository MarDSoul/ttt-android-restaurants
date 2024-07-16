import java.util.Properties

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.hilt)
	alias(libs.plugins.ksp)
}

val apikey = rootProject.file("apikey.properties")
val apikeyProperties = Properties()
apikeyProperties.load(apikey.inputStream())

android {
	namespace = "ttt.mardsoul.restaurants"
	compileSdk = 34

	defaultConfig {
		applicationId = "ttt.mardsoul.restaurants"
		minSdk = 29
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		buildConfigField("String", "TOKEN", apikeyProperties.getProperty("TOKEN"))
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
		compose = true
		buildConfig = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.14"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(libs.androidx.lifecycle.viewmodel)

	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)

	implementation(libs.hilt.android)
	ksp(libs.hilt.compiler)

	implementation(libs.retrofit)
	implementation(libs.retrofit.converter)
	implementation(libs.gson)
	implementation(libs.coil)
}
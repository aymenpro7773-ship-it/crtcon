plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.google.ai.sample"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.google.ai.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.ai.client.generativeai:generativeai:0.2.0")
}

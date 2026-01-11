import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.withType
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    `maven-publish`
    signing
    id("org.jetbrains.dokka") version "1.9.0"
    id("tech.yanand.maven-central-publish") version ("1.3.0")
}

android {
    namespace = "com.kajlee.tnav"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    // 生成 sources.jar
    publishing {
        singleVariant("release") { withSourcesJar() }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    api(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// ------------------ Dokka Javadoc Jar ------------------
val dokkaJavadocJar by tasks.registering(Jar::class) {
    dependsOn(tasks.named("dokkaJavadoc"))
    archiveClassifier.set("javadoc")
    from(tasks.named("dokkaJavadoc").get().outputs)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                
                groupId = "com.kajlee"
                artifactId = "tnav"
                version = "0.0.1"

                artifact(dokkaJavadocJar.get())

                pom {
                    name.set("TNav Android Library")
                    description.set("TNav navigation library for Android with Jetpack Compose")
                    url.set("https://github.com/KaJInL/tnav")
                    licenses {
                        license {
                            name.set("Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("kajinl")
                            name.set("Kajin")
                            email.set("1215302367@qq.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/KaJInL/tnav")
                        developerConnection.set("scm:git:ssh://github.com/KaJInL/tnav")
                        url.set("https://github.com/KaJInL/tnav")
                    }
                }
            }
        }

        repositories {
            mavenLocal()
        }
    }
    
    tasks.withType<PublishToMavenRepository> {
        dependsOn(dokkaJavadocJar)
        dependsOn("assembleRelease")
    }
}

val localPropsFile = rootProject.file("local.properties")
val localProps = if (localPropsFile.exists()) {
    Properties().apply {
        load(localPropsFile.inputStream())
    }
} else {
    Properties()
}

// 覆盖 gradle.properties 中的变量
localProps.getProperty("signing.keyId")?.let { project.extra["signing.keyId"] = it }
localProps.getProperty("signing.password")?.let { project.extra["signing.password"] = it }
localProps.getProperty("signing.secretKeyRingFile")?.let { project.extra["signing.secretKeyRingFile"] = it }
localProps.getProperty("authTokenValue")?.let { project.extra["authTokenValue"] = it }

signing {
    val signingKeyId = localProps.getProperty("signing.keyId")
    val signingPassword = localProps.getProperty("signing.password")
    val signingSecretKeyRingFile = localProps.getProperty("signing.secretKeyRingFile")
    
    if (signingKeyId != null && signingPassword != null && signingSecretKeyRingFile != null) {
        sign(publishing.publications)
    }
}

mavenCentral {
    localProps.getProperty("authTokenValue")?.let {
        authToken = it
        publishingType = "USER_MANAGED"
        maxWait = 60
    }
}


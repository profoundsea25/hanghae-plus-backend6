import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":common"))
    implementation(project(":domain:post:post-application"))
    implementation(project(":domain:post:post-adapter-in"))
    implementation(project(":domain:post:post-adapter-out"))
    implementation(project(":domain:auth:auth-application"))
    implementation(project(":domain:auth:auth-adapter-in"))
    implementation(project(":domain:auth:auth-adapter-out"))
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

plugins {
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":domain:auth:auth-application"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2:2.3.230") // runtimeOnly 만 제대로 작동함
}

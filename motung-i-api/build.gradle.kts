plugins {
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    /* implement */
    implementation(project(":motung-i-domain"))

    /* spring */
    implementation("org.springframework:spring-tx")

    /* security */
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
    testImplementation("org.springframework.security:spring-security-test")

    /* validation */
    implementation("org.springframework.boot:spring-boot-starter-validation")

    /* actuator */
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    /* jackson */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    /* s3 */
    implementation("software.amazon.awssdk:s3:2.30.21")

    /* geo */
    implementation("org.geolatte:geolatte-geojson:1.9.0")
}

tasks.getByName("jar") {
    enabled = false
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

// tasks.withType<Test> {
// 	useJUnitPlatform()
// }

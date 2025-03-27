plugins {
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

dependencies {
    /* jpa */
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    /* redis */
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    /* mysql */
    runtimeOnly("com.mysql:mysql-connector-j")
}

tasks.getByName("bootJar") {
    enabled = false
}
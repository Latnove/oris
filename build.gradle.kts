import org.gradle.kotlin.dsl.version

plugins {
    id("java")
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("war")
}

group = "org.example"
version = "1.0-SNAPSHOT"
val springVersion: String by project
val jakartaVersion: String by project
val hibernateVersion: String by project
val postgresVersion: String by project
val freemarkerVersion: String by project
val hikariVersion: String by project
val lombokVersion: String by project;
val springDataVersion: String by project;
val springSecurityVersion: String by project;

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.postgresql:postgresql:$postgresVersion")

    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

//    implementation("org.springframework:spring-webmvc:$springVersion")
//    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework:spring-jdbc:$springVersion")
//    implementation("org.springframework:spring-orm:$springVersion")
//    implementation("org.springframework:spring-context-support:$springVersion")
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.security:spring-boot-starter-security")
//    implementation("jakarta.servlet:jakarta.servlet-api:$jakartaVersion")
//    implementation("org.hibernate.orm:hibernate-core:$hibernateVersion")
//    implementation("org.postgresql:postgresql:$postgresVersion")
//    implementation("org.springframework.security:spring-security-taglibs:$springSecurityVersion")
//    compileOnly("org.projectlombok:lombok:$lombokVersion")
//    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
//    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
//    implementation("com.zaxxer:HikariCP:$hikariVersion")
//    implementation("org.freemarker:freemarker:$freemarkerVersion")
//    implementation("org.springframework.security:spring-security-core:${springSecurityVersion}")
//    implementation("org.springframework.security:spring-security-web:${springSecurityVersion}")
//    implementation("org.springframework.security:spring-security-config:${springSecurityVersion}")
//    implementation("org.springframework.security:spring-security-taglibs:${springSecurityVersion}")
}

//application {
//    mainClass.set("org.example.Main")
//}

tasks.test {
    useJUnitPlatform()
}

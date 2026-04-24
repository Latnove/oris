import org.gradle.kotlin.dsl.version
import java.util.Properties

plugins {
    id("java")
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("war")
    id("org.liquibase.gradle") version "2.2.2"
    id("jacoco")
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
    implementation("org.liquibase:liquibase-core:4.33.0")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    liquibaseRuntime("org.liquibase:liquibase-core:4.33.0")
    liquibaseRuntime("org.postgresql:postgresql:$postgresVersion")
    liquibaseRuntime("info.picocli:picocli:4.6.3")


    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

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

val props = Properties()
props.load(file("src/main/resources/db/liquibase.properties").inputStream())

liquibase {
    activities.register("main") {
        arguments = mapOf(
            "changeLogFile" to props.get("change-log-file"),
            "url" to props.get("url"),
            "username" to props.get("username"),
            "password" to props.get("password"),
            "driver" to props.get("driver-class-name"),
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

val jacocoExcludes = listOf(
    "**/org/example/dto/**",
    "**/org/example/model/**",
    "**/org/example/config/**"
)

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
    classDirectories.setFrom(files(classDirectories.files.map {
        fileTree(it).matching {
            exclude(jacocoExcludes)
        }
    }))
}

jacoco {
    toolVersion = "0.8.12"
    reportsDirectory.set(layout.buildDirectory.dir("jacoco"))
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = BigDecimal.valueOf(0.1)
            }
        }
    }

    classDirectories.setFrom(files(classDirectories.files.map {
        fileTree(it).matching {
            exclude(jacocoExcludes)
        }
    }))
}
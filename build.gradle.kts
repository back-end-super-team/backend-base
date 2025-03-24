plugins {
	java
	`maven-publish`
	id("org.springframework.boot") version "3.4.3"
}

apply(plugin = "io.spring.dependency-management")

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

group = "backend"
version = "0.0.1"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:3.2.0")
	implementation("org.springframework.kafka:spring-kafka")

	implementation("com.github.f4b6a3:ulid-creator:5.2.3")

	// Rate limiter
	implementation("com.bucket4j:bucket4j-core:8.10.1")
	implementation("com.bucket4j:bucket4j-redis:8.10.1")

	// Cache
	implementation("org.redisson:redisson-spring-boot-starter:3.31.0")

	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	implementation("io.minio:minio:8.5.17")
	implementation("org.modelmapper:modelmapper:3.2.2")

	annotationProcessor("org.projectlombok:lombok")

	compileOnly("org.projectlombok:lombok:1.18.36")

	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.rest-assured:rest-assured:5.5.1")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.jar {
	enabled = false
	manifest {
		attributes(
			"Main-Class" to "project.backend/base/BackendBaseApplication"
		)
	}
}

tasks.withType(JavaCompile::class) {
	options.encoding = "UTF-8"
}

tasks.register<Test>("blackboxTest") {
	description = "Runs blackbox tests."
	group = "verification"

	useJUnitPlatform()

	filter {
		includeTestsMatching("backend.base.blackbox.**")
	}
}

tasks.register<Test>("componentTest") {
	description = "Runs component tests."
	group = "verification"

	useJUnitPlatform()

	filter {
		includeTestsMatching("backend.base.component.**")
	}
}

tasks.register<Test>("unitTest") {
	description = "Runs unit tests."
	group = "verification"

	useJUnitPlatform()

	filter {
		includeTestsMatching("backend.base.unit.**")
	}
}

publishing {
	repositories {
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/back-end-super-team/backend-base")
			credentials {
				username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
				password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
			}
		}
	}
	publications {
		register<MavenPublication>("gpr") {
			from(components["java"])
		}
	}
}
import org.springframework.boot.gradle.tasks.bundling.BootJar

val bootJar: BootJar by tasks
bootJar.enabled = true

dependencies {
    implementation(project(":api"))
    implementation(project(":infra"))
    implementation(project(":core"))
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.data:spring-data-commons")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

plugins {
    id("java")
}

group = "net.weesli"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.airlift:aircompressor:2.0.2")
    implementation("com.dslplatform:dsl-json:2.0.2")
    annotationProcessor("com.dslplatform:dsl-json:2.0.2")
    implementation("javax.json.bind:javax.json.bind-api:1.0")
}

tasks.test {
    useJUnitPlatform()
}
java{
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
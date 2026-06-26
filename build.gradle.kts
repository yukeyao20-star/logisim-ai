plugins {
  java
  application
}

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

application {
  mainClass.set("com.logisim_ai.experiment.GenerateAndGate")
}

dependencies {
  implementation(files("third_party/logisim-evolution/build/libs/logisim-evolution-4.2.0dev-all.jar"))
}

tasks.withType<JavaCompile>().configureEach {
  options.encoding = "UTF-8"
}

tasks.withType<JavaExec>().configureEach {
  workingDir = rootProject.projectDir
}

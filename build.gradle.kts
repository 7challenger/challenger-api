plugins {
  scala
}

repositories {
  jcenter()
}

dependencies {
  implementation("org.scala-lang:scala-library:2.12.8")
  testRuntimeOnly("org.scala-lang.modules:scala-xml_2.12:1.2.0")
}

// tasks.register("example") {
//   println("example")
// }

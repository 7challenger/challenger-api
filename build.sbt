import Commands.helloAll

name := "challengerapi"
version := "0.0.1"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0"

lazy val root =
  (project in file("."))
  .settings(commands ++= Seq(helloAll))
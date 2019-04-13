name := "akka-dummy-average"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.12.8"

fork := true

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.22",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.22" % Test,
  "org.scalatest"  %% "scalatest" % "3.0.7" % Test
)

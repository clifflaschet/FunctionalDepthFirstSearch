lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.functional.ai.search",
      scalaVersion := "2.11.8",
      version      := "0.0.1-SNAPSHOT"
    )),
    name := "FunctionalDepthFirstSearch",

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.1" % Test,
      "org.scalaz" %% "scalaz-core" % "7.2.17"
    )
  )
lazy val sbtanks = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "3.3.1"
    )),
    name := "scalatest-example"
  )

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
  "org.scalafx" %% "scalafx" % "20.0.0-R31"
)

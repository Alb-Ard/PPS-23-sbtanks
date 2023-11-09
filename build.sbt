lazy val sbtanks = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.aas",
      scalaVersion := "3.3.1"
    )),
    name := "sbtanks"
  )

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "20.0.0-R31",
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
)

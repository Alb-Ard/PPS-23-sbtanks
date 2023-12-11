lazy val sbtanks = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.aas",
      scalaVersion := "3.3.1"
    )),
    name := "sbtanks"
  )

Compile / mainClass := Some("org.aas.sbtanks.Main")
assembly / mainClass := Some("org.aas.sbtanks.Main")
assembly / test := Some("org.ass.sbtanks")

ThisBuild / assemblyMergeStrategy := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "20.0.0-R31",
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
)

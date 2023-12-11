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
assembly / test := Some("org.aas.sbtanks")

ThisBuild / assemblyMergeStrategy := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
}


libraryDependencies ++= {
    lazy val osName = System.getProperty("os.name") match {
        case n if n.startsWith("Linux") => "linux"
        case n if n.startsWith("Mac") => "mac"
        case n if n.startsWith("Windows") => "win"
        case _ => throw new Exception("Unknown platform!")
    }
    Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "20" classifier osName)
}

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "20.0.0-R31",
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
)

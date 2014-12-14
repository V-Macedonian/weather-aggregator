import sbt._

object ApplicationBuild extends Build {

  val appName         = "modern-web-template"
  val appVersion      = "0.1-SNAPSHOT"
  val scalaVersion    = "2.11.1"

  val appDependencies = Seq(
    "com.google.inject" % "guice" % "3.0",
    "javax.inject" % "javax.inject" % "1",
    "org.reactivemongo" %% "reactivemongo" % "0.10.0",
    "org.reactivemongo" %% "play2-reactivemongo" % "0.10.2",
     ws,
    "org.mockito" % "mockito-core" % "1.9.5" % "test"
  )

  val main = Project(appName, file(".")).enablePlugins(play.PlayScala).settings(
    version := appVersion,
    libraryDependencies ++= appDependencies
  )


}

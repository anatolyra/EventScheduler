name := "EventScheduler"

version := "1.0"

lazy val `eventscheduler` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++=
  Seq(
    jdbc ,
    anorm ,
    cache ,
    ws,
    "org.sorm-framework" % "sorm" % "0.3.16",
    "com.h2database" % "h2" % "1.4.185",
    "org.quartz-scheduler" % "quartz" % "2.2.1" )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  
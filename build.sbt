name := "simple_scheduler_play"
 
version := "1.0" 
      
lazy val `simple_scheduler_play` = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(ScalikejdbcPlugin)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

val scalikejdbcVersion = "3.5.0"

libraryDependencies ++= Seq(
  // "com.h2database"         %  "h2"                           % "1.4.200", // your jdbc driver here
  "org.scalikejdbc"        %% "scalikejdbc"                  % scalikejdbcVersion,
  "org.scalikejdbc"        %% "scalikejdbc-config"           % scalikejdbcVersion,
  "org.scalikejdbc"        %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.5",
  "org.scalikejdbc"        %% "scalikejdbc-test"             % scalikejdbcVersion % "test"
)


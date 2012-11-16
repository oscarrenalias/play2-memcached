import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play2-memcached-fork"
    val appVersion      = "0.2.3-FORK-SNAPSHOT"

  val appDependencies = Seq(
    "spy" % "spymemcached" % "2.8.4",
    "play" %% "play" % "2.0"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    organization := "com.github.mumoshu",
    resolvers += "Spy Repository" at "http://files.couchbase.com/maven2"
  )

 
}

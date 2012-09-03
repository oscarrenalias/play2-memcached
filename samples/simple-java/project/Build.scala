import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "simple-java"
    val appVersion      = "1.0-SNAPSHOT"

	val appDependencies = Seq(
	    "spy" % "spymemcached" % "2.6",
	    "play" %% "play" % "2.0"
	  )

	  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
	    organization := "com.github.mumoshu",
	    resolvers += "Spy Repository" at "http://files.couchbase.com/maven2"
	  )

}

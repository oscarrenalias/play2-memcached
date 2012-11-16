Memcached Plugin for Play framework 2.0
---------------------------------------

Memcache-based caching backend for Play 2.0.x, forked from https://github.com/mumoshu/play2-memcached.

Using spymemcached 2.8 internally.

## Usage

The plugin is not provided via a Maven repository so it must be compiled and deployed to the Play 2.0.x application as a JAR file as an unmanaged dependency.

Use play package to build a JAR file and move target/scala-2.9.1/play2-memcached-fork_2.9.1-0.2.1-FORK-SNAPSHOT.jar to the lib/folder of your Play application.

Alternatively the module can be deployed to a Maven repository for improved dependency management.

## Configuration
=======
```scala
  val appDependencies = Seq(
    "com.github.mumoshu" %% "play2-memcached" % "0.2.3-SNAPSHOT"
  )
  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    resolvers += "Sonatype OSS Snapshots Repository" at "http://oss.sonatype.org/content/groups/public",
    resolvers += "Spy Repository" at "http://files.couchbase.com/maven2" // required to resolve `spymemcached`, the plugin's dependency.
  )
```

Create file play.plugins and save it somewhere in the classpath (a good place would be the conf/ folder):

```
5000:com.github.mumoshu.play2.memcached.MemcachedPlugin
```

In application.conf, disable the default Ehcache plugin for the cache:

```
  ehcacheplugin=disabled
```

And configure the memcache host data, including the port number:

```
  memcached.host="127.0.0.1:11211"
```

If you have multiple memcached instances over different host names or IP addresses, provide them like:

```
  memcached.1.host="mumocached1:11211"
  memcached.2.host="mumocached2:11211"
```

## Usage

The `play.api.cache.Cache` object is used just like with the default implementation:

```scala
 Cache.set("key", "theValue")
```

This way, memcached tries to retain the stored value eternally.
Of course Memcached does not guarantee eternity of the value, nor can it retain the value on restart.

If you want the value expired after some time:

```scala
 Cache.set("key", "theValueWithExpirationTime", 3600)
 // The value expires after 3600 seconds.
```

To get the value for a key:

```scala
 val theValue = Cache.getAs[String]("key")
```

You can remove the value (It's not yet a part of Play 2.0's Cache API, though):

```scala
 play.api.Play.current.plugin[MemcachedPlugin].get.api.remove("keyToRemove")
```

## Additional configurations

### Disabling the plugin

You can disable the plugin in a similar manner to Play's build-in Ehcache Plugin.
To disable the plugin in `application.conf`:

```
  memcachedplugin=disabled
```

### Authentication with SASL

If you memcached requires the client an authentication with SASL, provide username/password like:

```
  memcached.user=user
  memcached.password=password
```

### Configure logging

By default, the plugin (or the spymemcached under the hood) does not output any logs at all.
If you need to peek into what's going on, set the log level like:

```
  logger.memcached=DEBUG
```

## Deploying to Heroku

Heroku provides a memcache service that seamlessly works with this plugin, 

After enabling Heroku's memcache service for our application, the memcache server configuration should not be hardcoded but retrieved from the Heroku environment as follows:

```
ehcacheplugin=disabled
memcachedplugin=enabled
memcached.user=${MEMCACHE_USERNAME}
memcached.password=${MEMCACHE_PASSWORD}
memcached.host=${MEMCACHE_SERVERS}":11211"
```

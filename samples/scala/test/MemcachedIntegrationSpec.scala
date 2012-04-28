import com.github.mumoshu.play2.memcached.MemcachedPlugin
import org.specs2.specification.Scope
import play.api.cache.Cache
import play.api.Play.current
import play.api.test.{FakeApplication, TestServer}

object MemcachedIntegrationSpec extends ServerIntegrationSpec {

  sequential

  val additionalConfiguration = Map(
    "ehcacheplugin" -> "disabled",
    "memcached.host" -> "127.0.0.1:11211"
  )

  trait defaultContext extends Scope {

    val key = "memcachedIntegrationSpecKey"
    val value = "value"
    val expiration = 1000

  }

  "play.api.cache.Cache" should {

    "clear the stored data when setting a null value" in new defaultContext {

      Cache.set(key, value, expiration)

      Cache.get(key) must be some (value)
      current.plugin[MemcachedPlugin].map(_.api).get.get(key) must be some (value)

      Cache.set(key, null) should throwA[NullPointerException]

      // TODO Cache.set(key, null)
      // TODO Cache.get(key) must be none
    }
  }

  "The Cache API implementation of MemcachedPlugin" should {

    "clear the stored data when setting a null value" in new defaultContext {

      val api = current.plugin[MemcachedPlugin].map(_.api).get

      api.set(key, value, expiration)
      api.get(key) must be some (value)

      api.set(key, null, expiration) should throwA[NullPointerException]

      // TODO api.set(key, null, expiration)
      // TODO api.get(key) must be none
    }
  }

}

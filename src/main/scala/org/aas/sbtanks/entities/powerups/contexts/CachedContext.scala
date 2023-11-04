package org.aas.sbtanks.entities.powerups.contexts


class CachedContext[T]:
    private var cache: Option[T] = None

    def provide(data: T): T =
      if (cache.isEmpty) {
        cache = Some(data)
      }
      cache.get

    def getAndClear(): Option[T] =
      val result = cache
      clearCache()
      result

    private def clearCache(): Unit =
      cache = None

object CachedContext:
    def apply[T](): CachedContext[T] = new CachedContext[T]()








package org.aas.sbtanks.entities.powerups.contexts

/**
 * A generic class representing a cached context for a specific type.
 *
 * @tparam T The type of data to be cached.
 */
class CachedContext[T]:
    private var cache: Option[T] = None

    /**
     * Provides the data to be cached, storing it in the cache if it is not already present.
     *
     * @param data The data to be cached.
     * @return The cached data.
     */
    def provide(data: T): T =
      if (cache.isEmpty) {
        cache = Some(data)
      }
      cache.get

    /**
     * Retrieves the cached data and clears the cache.
     *
     * @return Option containing the cached data, or None if the cache is empty.
     */
    def getAndClear(): Option[T] =
      val result = cache
      clearCache()
      result

    private def clearCache(): Unit =
      cache = None

object CachedContext:
    def apply[T](): CachedContext[T] = new CachedContext[T]()








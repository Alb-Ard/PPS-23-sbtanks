package org.aas.sbtanks.entities.powerups.contexts


class MapContext[E, V]:
    private var entityMap: Map[E, V] = Map.empty

    /**
     * Registers an entity in the internal map with the specified value.
     *
     * @param entity The entity to be registered.
     * @param value  The value associated with the entity.
     */
    def registerEntity(entity: E, value: V): Unit =
        entityMap = entityMap + (entity -> value)


    /**
     * Retrieves the value associated with the given entity from the internal map.
     *
     * @param entity The entity for which the value is requested.
     * @return Option containing the value associated with the entity, or None if the entity is not registered.
     */
    def getValue(entity: E): Option[V] =
        entityMap.get(entity)


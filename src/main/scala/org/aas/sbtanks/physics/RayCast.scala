package org.aas.sbtanks.physics

object Raycast:
    extension (physicsContainer: PhysicsContainer)
        /**
          * Casts a vertical ray on the physics container and checks ALL the colliders that intersect it
          *
          * @param fromX The starting X position of the ray
          * @param fromY The starting Y position of the ray
          * @param length The length of the ray, can be negative to cast backwards or None to cast a positive infinite ray
          * @param layers The layers that the cast will check for collisions 
          * @param exclusions A sequence of colliders that should be excluded from the result
          * 
          * @return A sequence of all the colliders intersected by the ray, orederer by distance from the nearest to the farthest
          */
        def verticalRayCast(fromX: Double, fromY: Double, length: Option[Double], layers: Seq[CollisionLayer], exclusions: Seq[Collider]) =
            physicsContainer.getBoxOverlaps(AABB(fromX, fromY, 0D, length.getOrElse(Double.MaxValue)), layers, exclusions).sortWith(isNearest(fromX, fromY))

        /**
         * Casts a horizontal ray on the physics container and checks ALL the colliders that intersect it
         *
         * @param fromX The starting X position of the ray
         * @param fromY The starting Y position of the ray
         * @param length The length of the ray, can be negative to cast backwards or None to cast a positive infinite ray
         * @param layers The layers that the cast will check for collisions 
         * @param exclusions A sequence of colliders that should be excluded from the result
         * 
         * @return A sequence of all the colliders intersected by the ray, orederer by distance from the nearest to the farthest
         */
        def horizontalRayCast(fromX: Double, fromY: Double, length: Option[Double], layers: Seq[CollisionLayer], exclusions: Seq[Collider]) =
            physicsContainer.getBoxOverlaps(AABB(fromX, fromY, length.getOrElse(Double.MaxValue), 0D), layers, exclusions).sortWith(isNearest(fromX, fromY))

    /**
      * Checks if a given collider is the nearest to a source point in respect to another collider
      *
      * @param sourceX The source point X
      * @param sourceY The source point Y
      * @param a The collider to check if it is the nearest
      * @param b The collider to check against
      * 
      * @return true if a's distance to the source point is less that the distance to b, false otherwise
      */
    private def isNearest(sourceX: Double, sourceY: Double)(a: Collider, b: Collider): Boolean =
        val boundingBoxA = a.boundingBox
        val distanceToASquared = distanceToSquared(sourceX, sourceY, boundingBoxA.x, boundingBoxA.y)
        val boundingBoxB = b.boundingBox
        val distanceToBSquared = distanceToSquared(sourceX, sourceY, boundingBoxB.x, boundingBoxB.y)
        distanceToASquared < distanceToBSquared
    
    private def distanceToSquared(x1: Double, y1: Double, x2: Double, y2: Double) =
        (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)
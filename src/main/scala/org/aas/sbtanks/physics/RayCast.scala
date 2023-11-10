package org.aas.sbtanks.physics

object Raycast:
    extension (physicsContainer: PhysicsContainer)
        def verticalRayCast(fromX: Double, fromY: Double, maxLength: Option[Double], layers: Seq[CollisionLayer], exclusions: Seq[Collider]) =
            physicsContainer.getBoxOverlaps(AABB(fromX, fromY, 0D, maxLength.getOrElse(Double.MaxValue)), layers, exclusions)

        def horizontalRayCast(fromX: Double, fromY: Double, maxLength: Option[Double], layers: Seq[CollisionLayer], exclusions: Seq[Collider]) =
            physicsContainer.getBoxOverlaps(AABB(fromX, fromY, maxLength.getOrElse(Double.MaxValue), 0D), layers, exclusions)
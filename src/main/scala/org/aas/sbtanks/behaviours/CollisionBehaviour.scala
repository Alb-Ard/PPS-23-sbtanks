package org.aas.sbtanks.behaviours

import org.aas.sbtanks.physics.AABB
import org.aas.sbtanks.physics.PhysicsWorld
import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.event.EventSource

trait CollisionBehaviour(sizeX: Double, sizeY: Double, override val layer: CollisionLayer, override val layerMasks: Seq[CollisionLayer]) extends Collider:
    this: PositionBehaviour =>

    val overlapping = EventSource[Seq[Collider]]

    PhysicsWorld.registerCollider(this)

    def overlapsAnything = overlappedColliders.nonEmpty 

    def overlappedColliders = 
        val overlaps = PhysicsWorld.getOverlaps(this)
        overlapping(overlaps)
        overlaps

    override def boundingBox = AABB(positionX, positionY, sizeX, sizeY)
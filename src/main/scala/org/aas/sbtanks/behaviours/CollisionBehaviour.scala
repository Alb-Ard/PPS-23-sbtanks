package org.aas.sbtanks.behaviours

import org.aas.sbtanks.physics.AABB
import org.aas.sbtanks.physics.PhysicsWorld
import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.physics.CollisionLayer

trait CollisionBehaviour(sizeX: Double, sizeY: Double, override val layer: CollisionLayer, override val layerMasks: Seq[CollisionLayer]) extends Collider:
    this: PositionBehaviour =>

    PhysicsWorld.registerCollider(this)

    def overlapsAnything: Boolean = PhysicsWorld.checkOverlap(this)

    override def boundingBox = AABB(positionX, positionY, sizeX, sizeY)
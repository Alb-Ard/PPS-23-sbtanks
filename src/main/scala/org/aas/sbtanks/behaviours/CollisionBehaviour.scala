package org.aas.sbtanks.behaviours

import org.aas.sbtanks.physics.AABB
import org.aas.sbtanks.physics.PhysicsWorld
import org.aas.sbtanks.physics.Collider

trait CollisionBehaviour(sizeX: Double, sizeY: Double) extends Collider:
    this: PositionBehaviour =>

    PhysicsWorld.registerCollider(this)

    def overlapsAnything: Boolean = PhysicsWorld.checkOverlap(this)

    def boundingBox = AABB(positionX, positionY, sizeX, sizeY)
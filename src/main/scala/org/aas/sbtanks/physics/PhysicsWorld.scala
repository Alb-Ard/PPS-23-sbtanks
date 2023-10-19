package org.aas.sbtanks.physics

import org.aas.sbtanks.behaviours.CollisionBehaviour

object PhysicsWorld:
    private var colliders = List.empty[Collider]

    def registerCollider(collider: Collider) =
        colliders = collider :: colliders

    def unregisterCollider(collider: Collider) =
        colliders = colliders.filterNot(collider.equals)

    def checkOverlap(collider: Collider) =
        !colliders.filterNot(collider.equals).forall(c => !c.boundingBox.checkOverlap(collider.boundingBox) || !collider.layerMasks.contains(c.layer))
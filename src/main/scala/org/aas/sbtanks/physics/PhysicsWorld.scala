package org.aas.sbtanks.physics

import org.aas.sbtanks.behaviours.CollisionBehaviour

object PhysicsWorld:
    private var colliders = List.empty[Collider]

    def registerCollider(collider: Collider) =
        colliders = collider :: colliders

    def hasCollider(collider: Collider) = 
        colliders contains collider

    def unregisterCollider(collider: Collider) =
        colliders = colliders.filterNot(collider.equals)

    def clearColliders() =
        colliders = List.empty

    def getOverlaps(collider: Collider) =
        colliders.filterNot(collider.equals)
            .filter(c => c.boundingBox.checkOverlap(collider.boundingBox) && collider.layerMasks.contains(c.layer))
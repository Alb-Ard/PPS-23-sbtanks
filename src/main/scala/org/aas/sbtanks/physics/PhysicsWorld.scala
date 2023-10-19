package org.aas.sbtanks.physics

import org.aas.sbtanks.behaviours.CollisionBehaviour

object PhysicsWorld:
    private var colliders = List.empty[Collider]

    def registerCollider(collider: Collider) =
        colliders = collider :: colliders

    def unregisterCollider(collider: Collider) =
        colliders = colliders filterNot(c => c == collider)

    def checkOverlap(collider: Collider):Boolean =
        checkOverlap(collider.boundingBox, collider)
        
    def checkOverlap(boundingBox: AABB, exclusion: Collider):Boolean =
        colliders.filterNot(c => c == exclusion).forall(c => c.boundingBox.checkOverlap(boundingBox))
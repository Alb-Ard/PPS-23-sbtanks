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
        getBoxOverlaps(collider.boundingBox, collider.layerMasks, Seq(collider))

    def getBoxOverlaps(box: AABB, layers: Seq[CollisionLayer], ignoredColliders: Seq[Collider]) =
        colliders.filterNot(ignoredColliders.contains)
            .filter(c => c.boundingBox.checkOverlap(box) && (layers.isEmpty || layers.contains(c.layer)))
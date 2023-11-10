package org.aas.sbtanks.physics

import org.aas.sbtanks.behaviours.CollisionBehaviour

trait PhysicsContainer:
    private var colliders = Seq.empty[Collider]

    def registerCollider(collider: Collider) =
        colliders = colliders :+ collider

    def hasCollider(collider: Collider) = 
        colliders contains collider

    def unregisterCollider(collider: Collider) =
        colliders = colliders.filterNot(collider.equals)

    def clearColliders() =
        colliders = Seq.empty

    def getOverlaps(collider: Collider) =
        getBoxOverlaps(collider.boundingBox, collider.layerMasks, Seq(collider))

    def getBoxOverlaps(box: AABB, layers: Seq[CollisionLayer], ignoredColliders: Seq[Collider]) =
        val clampedBox = AABB(box.x, box.y, Math.max(0.1D, box.width), Math.max(0.1D, box.height))
        colliders.filterNot(ignoredColliders.contains)
            .filter(c => c.boundingBox.checkOverlap(box) && (layers.isEmpty || layers.contains(c.layer)))

object PhysicsWorld extends PhysicsContainer
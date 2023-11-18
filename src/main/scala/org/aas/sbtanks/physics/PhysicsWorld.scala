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
        layers.length match
            case 0 => Seq.empty
            case _ =>
                val clampedBox = box.normalized.clamped(0.1D)
                colliders.filterNot(ignoredColliders.contains)
                    .filter(c => {
                        //println(c.toString() + " " + clampedBox.toString())
                        c.boundingBox.checkOverlap(clampedBox) && layers.contains(c.layer)
                    })

object PhysicsWorld extends PhysicsContainer
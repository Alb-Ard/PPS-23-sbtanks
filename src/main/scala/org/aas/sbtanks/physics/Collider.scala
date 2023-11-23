package org.aas.sbtanks.physics

import org.aas.sbtanks.behaviours.CollisionBehaviour

trait Collider:
    val layer: CollisionLayer
    val layerMasks: Seq[CollisionLayer]
    
    def boundingBox: AABB
    def overlapsChanged(overlappingColliders: Seq[Collider]): Unit
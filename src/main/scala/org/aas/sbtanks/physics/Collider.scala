package org.aas.sbtanks.physics

import org.aas.sbtanks.behaviours.CollisionBehaviour

trait Collider:
    def boundingBox: AABB
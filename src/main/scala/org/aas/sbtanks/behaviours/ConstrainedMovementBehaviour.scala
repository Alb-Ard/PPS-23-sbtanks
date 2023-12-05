package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.physics.AABB.checkOverlap
import org.aas.sbtanks.physics.PhysicsContainer

trait ConstrainedMovementBehaviour(using physics: PhysicsContainer) extends MovementBehaviour:
    this: PositionBehaviour with CollisionBehaviour =>

    override def moveRelative(amountX: Double, amountY: Double) =
        if testMoveRelative(amountX, amountY) then
            super.moveRelative(amountX, amountY)
        this
    
    def testMoveRelative(amountX: Double, amountY: Double) =
        val startingBox = boundingBox
        val movedBox = boundingBox.copy(x = startingBox.x + amountX, y = startingBox.y + amountY)
        physics.getBoxOverlaps(movedBox, layerMasks, Seq(this)).isEmpty
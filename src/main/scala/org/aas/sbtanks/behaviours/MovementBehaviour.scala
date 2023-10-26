package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.physics.AABB.checkOverlap

trait MovementBehaviour:
    this: PositionBehaviour =>

    def moveRelative(amountX: Double, amountY: Double) = 
        setPosition(positionX + amountX, positionY + amountY)

trait ConstrainedMovementBehaviour extends MovementBehaviour:
    this: PositionBehaviour with CollisionBehaviour =>

    override def moveRelative(amountX: Double, amountY: Double) =
        val previousX = positionX
        val previousY = positionY
        super.moveRelative(amountX, amountY)
        if overlapsAnything then
            setPosition(previousX, previousY)
        this
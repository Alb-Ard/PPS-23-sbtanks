package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait SteppedMovementDirectionBehaviour(stepSpeed: Double = 1) extends SteppedBehaviour:
    val stepMoved = EventSource[(Double, Double)]

    private var moveDirection = (0D, 0D)

    def step(delta: Double): Unit =
        stepMoved(moveDirection(0) * delta * stepSpeed, moveDirection(1) * delta * stepSpeed)

    def moveTowards(directionX: Double, directionY: Double): Unit = 
        moveDirection = (directionX, directionY)
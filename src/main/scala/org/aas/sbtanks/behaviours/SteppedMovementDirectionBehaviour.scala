package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait SteppedMovementDirectionBehaviour(private var stepSpeed: Double = 1D) extends SteppedBehaviour:
    val directionStepped = EventSource[(Double, Double)]
    val directionChanged = EventSource[(Double, Double)]

    private var moveDirection = (0D, 0D)
    private var lastDirection = (0D, 0D)

    def direction = lastDirection

    def setSpeed(newSpeed: Double): SteppedMovementDirectionBehaviour = 
        stepSpeed = newSpeed
        this

    def step(delta: Double): Unit =
        directionStepped(moveDirection(0) * delta * stepSpeed, moveDirection(1) * delta * stepSpeed)

    def moveTowards(directionX: Double, directionY: Double): Unit = 
        moveDirection = (directionX, directionY)
        if directionX != 0 || directionY != 0 then
            lastDirection = moveDirection
            directionChanged(direction)
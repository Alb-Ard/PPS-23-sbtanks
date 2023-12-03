package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait DirectionBehaviour:
    val directionChanged = EventSource[(Double, Double)]

    private var direction: (Double, Double) = (0D, 0D)

    def directionX = direction(0)
    def directionY = direction(1)

    def setDirection(directionX: Double, directionY: Double) = 
        val oldDirection = direction;
        direction = (directionX, directionY)
        if direction != oldDirection then
            directionChanged(direction)
        this
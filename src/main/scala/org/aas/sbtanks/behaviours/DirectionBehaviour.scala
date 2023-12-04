package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait DirectionBehaviour(startingDirectionX: Double = 0D, startingDirectionY: Double = 1D):
    val directionChanged = EventSource[(Double, Double)]

    private var direction: (Double, Double) = (startingDirectionX, startingDirectionY)
    private var lastValidDirection: Option[(Double, Double)] = Option.empty

    def directionX = direction(0)
    def directionY = direction(1)

    def lastValidDirectionX = lastValidDirection.map(d => d(0))
    def lastValidDirectionY = lastValidDirection.map(d => d(1))

    def setDirection(directionX: Double, directionY: Double) = 
        val oldDirection = direction;
        direction = (directionX, directionY)
        lastValidDirection = direction match
            case (x, y) if x != 0 || y != 0 => Option(direction)
            case _ => lastValidDirection
        if direction != oldDirection then
            directionChanged(direction)
        this
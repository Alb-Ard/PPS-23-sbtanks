package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait MovementBehaviour(startingX: Double = 0, startingY: Double = 0):
    val moved = EventSource[(Double, Double)]

    private var position = (startingX, startingY)

    def positionX = position(0)

    def positionY = position(1)

    def moveAbsolute(x: Double, y: Double) = 
        position = (x, y)
        moved(position)

    def moveRelative(amountX: Double, amountY: Double) =
        moveAbsolute(position(0) + amountX, position(1) + amountY)
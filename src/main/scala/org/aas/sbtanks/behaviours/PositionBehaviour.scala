package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait PositionBehaviour(startingX: Double = 0, startingY: Double = 0):
    val positionChanged = EventSource[(Double, Double)]
    
    private var position = (startingX, startingY)

    def positionX = position(0)
    def positionY = position(1)
    
    def setPosition(x: Double, y: Double) = 
        position = (x, y)
        positionChanged(position)
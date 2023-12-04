package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait PositionBehaviour(val startingX: Double = 0, val startingY: Double = 0):
    val positionChanged = EventSource[(Double, Double)]
    
    private var position: (Double, Double) = (startingX, startingY)

    def positionX = position(0)
    def positionY = position(1)
    
    def setPosition(x: Double, y: Double) = 
        position = (x, y)
        positionChanged(position)
        this
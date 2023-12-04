package org.aas.sbtanks.behaviours

trait MovementBehaviour:
    this: PositionBehaviour =>

    def moveRelative(amountX: Double, amountY: Double) = 
        setPosition(positionX + amountX, positionY + amountY)


package org.aas.sbtanks.entities.tank.behaviours

trait TankMovementBehaviour(startingX: Double = 0, startingY: Double = 0):
    private var position = (startingX, startingY)

    def positionX: Double = position(0)

    def positionY: Double = position(1)

    def move(amountX: Double, amountY: Double) = 
        position = (position(0) + amountX, position(1) + amountY)

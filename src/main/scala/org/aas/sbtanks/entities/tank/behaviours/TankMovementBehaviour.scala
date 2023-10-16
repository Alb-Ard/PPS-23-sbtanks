package org.aas.sbtanks.entities.tank.behaviours

trait TankMovementBehaviour(startingX: Int = 0, startingY: Int = 0):
    private var position = (startingX, startingY)

    def positionX: Int = position(0)

    def positionY: Int = position(1)

    def move(amountX: Int, amountY: Int) = 
        position = (position(0) + amountX, position(1) + amountY)

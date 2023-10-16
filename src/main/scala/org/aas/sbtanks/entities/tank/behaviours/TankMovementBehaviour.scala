package org.aas.sbtanks.entities.tank.behaviours

trait TankMovementBehaviour:
    private var position = (0, 0)

    def positionX: Int = position(0)

    def positionY: Int = position(1)

    def move(amountX: Int, amountY: Int) = 
        position = (position(0) + amountX, position(1) + amountY)

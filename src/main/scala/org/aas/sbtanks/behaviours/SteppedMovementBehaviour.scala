package org.aas.sbtanks.behaviours

trait SteppedTankMovementBehaviour(stepSpeed: Double = 1) 
    extends TankMovementBehaviour
    with SteppedBehaviour:

    private var moveDirection = (0D, 0D)

    def step(delta: Double): Unit =
        super.move(moveDirection(0) * delta * stepSpeed, moveDirection(1) * delta * stepSpeed)

    override def move(amountX: Double, amountY: Double): Unit = 
        moveDirection = (amountX, amountY)
package org.aas.sbtanks.entities.tanks.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.aas.sbtanks.entities.tank.behaviours.TankMovementBehaviour
import org.scalatest.matchers.should.Matchers

class TankMovementSpec extends AnyFlatSpec with Matchers:
    def withTankMovement(test: (movementBehaviour: TankMovementBehaviour) => Any) =
        test(new Object() with TankMovementBehaviour)

    def testMovement(movementBehaviour: TankMovementBehaviour, amountX: Int, amountY: Int) =
        val previousPositionX = movementBehaviour.positionX
        val previousPositionY = movementBehaviour.positionY
        movementBehaviour.move(amountX, amountY)
        (movementBehaviour.positionX - previousPositionX) should be (amountX)
        (movementBehaviour.positionY - previousPositionY) should be (amountY)

    "A Tank" should "be able to move up" in withTankMovement { movementBehaviour =>
        testMovement(movementBehaviour, 0, 1)
    }

    it should "be able to move down" in withTankMovement { movementBehaviour =>
        testMovement(movementBehaviour, 0, -1)
    }

    it should "be able to move left" in withTankMovement { movementBehaviour =>
        testMovement(movementBehaviour, -1, 0)    
    }

    it should "be able to move right" in withTankMovement { movementBehaviour =>
        testMovement(movementBehaviour, 1, 0)    
    }
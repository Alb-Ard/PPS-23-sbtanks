package org.aas.sbtanks.entities.tanks.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.aas.sbtanks.entities.tank.behaviours.TankMovementBehaviour
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.entities.tank.behaviours.SteppedTankMovementBehaviour

class SteppedTankMovementSpec extends AnyFlatSpec with Matchers:
    val stepSpeed = 1

    def withTankMovement(test: (movementBehaviour: SteppedTankMovementBehaviour) => Any) =
        test(new Object() with TankMovementBehaviour(0, 0) with SteppedTankMovementBehaviour(stepSpeed))

    def testMovement(movementBehaviour: SteppedTankMovementBehaviour, amountX: Double, amountY: Double)(stepCount: Int, delta: Double) =
        movementBehaviour.move(amountX, amountY)
        0 until stepCount foreach { s =>
            val previousPositionX = movementBehaviour.positionX
            val previousPositionY = movementBehaviour.positionY
            movementBehaviour.step(delta) 
            (movementBehaviour.positionX - previousPositionX) should be (amountX * delta * stepSpeed +- 0.0001)
            (movementBehaviour.positionY - previousPositionY) should be (amountY * delta * stepSpeed +- 0.0001)
        }

    "A Tank with stepped movement" should "be able to move up for a certain amount of steps with a given delta time" in withTankMovement { movementBehaviour =>
        testMovement(movementBehaviour, 0, 1)(10, 0.01)
    }

    it should "be able to move down for a certain amount of steps with a given delta time" in withTankMovement { movementBehaviour =>
        testMovement(movementBehaviour, 0, -1)(10, 0.01)
    }

    it should "be able to move left for a certain amount of steps with a given delta time" in withTankMovement { movementBehaviour =>
        testMovement(movementBehaviour, -1, 0)(10, 0.01)  
    }

    it should "be able to move right for a certain amount of steps with a given delta time" in withTankMovement { movementBehaviour =>
        testMovement(movementBehaviour, 1, 0)(10, 0.01)
    }
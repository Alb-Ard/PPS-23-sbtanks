package org.aas.sbtanks.entities.tanks.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour

class SteppedTankMovementSpec extends AnyFlatSpec with Matchers:
    val stepSpeed = 1

    def withDirectionMovement(test: (movementBehaviour: SteppedMovementDirectionBehaviour) => Any) =
        test(new Object() with SteppedMovementDirectionBehaviour(stepSpeed))

    "A stepped directional movement behaviour" should "be able to move in a given direction with a given delta time" in withDirectionMovement { movementBehaviour =>
        val stepDeltaTime = 0.01
        movementBehaviour.moveTowards(-2, 3)
        movementBehaviour.directionStepped += { (x, y) => 
            x should be (-2 * stepDeltaTime * stepSpeed)
            y should be (3 * stepDeltaTime * stepSpeed)
        }
        0 until 10 foreach { s =>
            movementBehaviour.step(stepDeltaTime) 
        }
    }
package org.aas.sbtanks.entities.tanks.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.behaviours.DirectionBehaviour

class SteppedTankMovementSpec extends AnyFlatSpec with Matchers:
    def withDirectionMovement(test: (movementBehaviour: DirectionBehaviour) => Any) =
        test(new Object() with DirectionBehaviour)

    "A stepped directional movement behaviour" should "be able to move in a given direction with a given delta time" in withDirectionMovement { movementBehaviour =>
        movementBehaviour.setDirection(-2, 3)
        movementBehaviour.directionX should be (-2)
        movementBehaviour.directionY should be (3)
    }
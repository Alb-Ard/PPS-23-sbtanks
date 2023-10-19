package org.aas.sbtanks.entities.tanks.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.behaviours.PositionBehaviour

class TankMovementSpec extends AnyFlatSpec with Matchers:
    def withMovement(test: (movementBehaviour: MovementBehaviour with PositionBehaviour) => Any) =
        test(new Object() with MovementBehaviour with PositionBehaviour)

    "A movement behaviour" should "be able to move up" in withMovement { movementBehaviour =>
        movementBehaviour.moveRelative(0, 1)
        movementBehaviour.positionX should be (0)
        movementBehaviour.positionY should be (1)
    }

    it should "be able to move down" in withMovement { movementBehaviour =>
        movementBehaviour.moveRelative(0, -1)
        movementBehaviour.positionX should be (0)
        movementBehaviour.positionY should be (-1)
    }

    it should "be able to move left" in withMovement { movementBehaviour =>
        movementBehaviour.moveRelative(-1, 0)
        movementBehaviour.positionX should be (-1)
        movementBehaviour.positionY should be (0)
    }

    it should "be able to move right" in withMovement { movementBehaviour =>
        movementBehaviour.moveRelative(1, 0)
        movementBehaviour.positionX should be (1)
        movementBehaviour.positionY should be (0)
    }

    it should "be able to move to an absolute position" in withMovement { movementBehaviour =>
        movementBehaviour.setPosition(3, -2)
        movementBehaviour.positionX should be (3)
        movementBehaviour.positionY should be (-2)
    }
package org.aas.sbtanks.entities.bullet.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class BulletMovementSpec extends AnyFlatSpec with Matchers:
    def withBulletMovement(test: (movementBehaviour: BulletMovementBehaviour) => Any) =
      test(new Object() with BulletMovementBehaviour)

    def testMovement(movementBehaviour: BulletMovementBehaviour, amountX: Int, amountY: Int) =
      val previousPositionX = movementBehaviour.positionX
      val previousPositionY = movementBehaviour.positionY
      movementBehaviour.move(amountX, amountY)
      (movementBehaviour.positionX - previousPositionX) should be(amountX)
      (movementBehaviour.positionY - previousPositionY) should be(amountY)

    //def testMovementInOneDirection(previousMovement: BulletMovementBehaviour, amountX: Int, amountY: Int) =
      //testMovement(previousMovement,amountX, amountY)



    "A Bullet" should "be able to move up" in withBulletMovement { movementBehaviour =>
      testMovement(movementBehaviour, 0, 1)
    }

    it should "be able to move down" in withBulletMovement { movementBehaviour =>
      testMovement(movementBehaviour, 0, -1)
    }

    it should "be able to move left" in withBulletMovement { movementBehaviour =>
      testMovement(movementBehaviour, -1, 0)
    }

    it should "be able to move right" in withBulletMovement { movementBehaviour =>
      testMovement(movementBehaviour, 1, 0)
    }

    it should "only move in one direction" in withBulletMovement { movementBehaviour =>
      val range = 1 to 10
      for(n <- range) {
        testMovement(movementBehaviour, 1, 0)
      }
    }

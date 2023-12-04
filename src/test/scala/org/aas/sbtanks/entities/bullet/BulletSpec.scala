package org.aas.sbtanks.entities.bullet

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DirectionBehaviour, PositionBehaviour, ConstrainedMovementBehaviour}
import org.aas.sbtanks.entities.tank.behaviours.TankMultipleShootingBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.behaviours.PositionMatchers
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.entities.bullet.controller.BulletController
import org.aas.sbtanks.physics.PhysicsContainer


class BulletSpec extends AnyFlatSpec with Matchers with PositionMatchers {
    import org.aas.sbtanks.entities.tank.structure.Tank.{FastTank, BasicTank}
    import org.aas.sbtanks.entities.tank.{TankData, TankDataUpdater}
    import org.aas.sbtanks.entities.tank.structure.Tank.{BasicTank, FastTank}
    import org.aas.sbtanks.entities.tank.structure.Tank

    val basicTank = new BasicTank() with PositionBehaviour() with DirectionBehaviour  with TankMultipleShootingBehaviour()
    basicTank.setDirection(1.0, 0.0)

    "A bullet" should "be created when a tank shoots" in {
        given PhysicsContainer = new Object() with PhysicsContainer
        for
            n <- 1 to 2
        do
            val shotBullets = basicTank.shoot(n, true)
            shotBullets should have length (n)
    }

    it should "have the same speed as the tank that shot it" in {
        given PhysicsContainer = new Object() with PhysicsContainer
        basicTank.shoot(1, true).head.speed should equal(basicTank.tankData.bulletSpeed)
    }

    it should "have the player flag set as it was passed" in {
        given PhysicsContainer = new Object() with PhysicsContainer
        basicTank.shoot(1, true).head.isPlayerBullet should be (true)
        basicTank.shoot(1, false).head.isPlayerBullet should be (false)
    }

    it should "have the same direction as the tank that shot it" in {
        given PhysicsContainer = new Object() with PhysicsContainer
        basicTank.shoot(1, true).head.directionX should equal(basicTank.directionX)
    }

    it should "have the correct position relative to the tank that shot it" in {
        given PhysicsContainer = new Object() with PhysicsContainer
        basicTank.shoot(1, true).head should have (
            positionX (basicTank.positionX + basicTank.directionX * basicTank.BULLET_OFFSET),
            positionY (basicTank.positionY + basicTank.directionY * basicTank.BULLET_OFFSET)
        )
    }

    it should "move in the same direction as when it was shot" in {
        given PhysicsContainer = new Object() with PhysicsContainer
        val bullets = basicTank.shoot(2, true)
        val controllers = bullets.map(b => BulletController(b, MockBulletView(), 1, 1, 1))
        val stepCount = 3
        for
            c <- controllers
            _ <- 0 until stepCount
        do
            c.step(1.0)
        for 
            i <- 0 until bullets.length
        do
            bullets(i) should have (
                positionX (basicTank.positionX + basicTank.BULLET_OFFSET * basicTank.directionX * (i + 1) + (basicTank.directionX * basicTank.tankData.bulletSpeed * stepCount)),
                positionY (basicTank.positionY + basicTank.BULLET_OFFSET * basicTank.directionY * (i + 1) + (basicTank.directionY * basicTank.tankData.bulletSpeed * stepCount))
            )
    }
}

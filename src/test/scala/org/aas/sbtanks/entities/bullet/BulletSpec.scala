package org.aas.sbtanks.entities.bullet

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.tank.behaviours.TankShootingBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class BulletSpec extends AnyFlatSpec with Matchers {
    import org.aas.sbtanks.entities.tank.structure.Tank.{FastTank, BasicTank}
    import org.aas.sbtanks.entities.tank.{TankData, TankDataUpdater}
    import org.aas.sbtanks.entities.tank.structure.Tank.{BasicTank, FastTank}
    import org.aas.sbtanks.entities.tank.structure.Tank


    val basicTank = new BasicTank() with PositionBehaviour() with DirectionBehaviour with TankShootingBehaviour()
    val fastTank = new FastTank()
    val basicBullet = basicTank.shoot()

    "a bullet" should "be created when a tank shoots" in {
        basicTank.shoot() should be(new Bullet(basicTank.tankData.bulletSpeed, false) with PositionBehaviour(basicTank.positionX + basicTank.directionX,
                                    basicTank.positionY + basicTank.directionY) with DirectionBehaviour
                                    with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                                    Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer)))
    }

    it should "have the same speed as the tank that shot it" in {
        basicBullet.speed should equal(basicTank.tankData.bulletSpeed)
    }


    it should "continue to move in one direction once shot" in {
        basicBullet.positionChanged(basicBullet.positionX + (basicBullet.directionX * basicBullet.speed),
                                    basicBullet.positionY + (basicBullet.directionY * basicBullet.speed))
        basicBullet.positionX should equal((basicTank.positionX + basicTank.directionX) * 2)
        basicBullet.positionY should equal((basicTank.positionY + basicTank.directionY) * 2)
    }


    it should "stop existing when it collides with something" in {

    }
}

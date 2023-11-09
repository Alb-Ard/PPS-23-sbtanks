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
    val fastTank = new FastTank() with PositionBehaviour() with DirectionBehaviour with TankShootingBehaviour()
    val basicBullet = basicTank.shoot()
    val fastBullet = fastTank.shoot()

    "a bullet" should "be created when a tank shoots" in {
        basicTank.shoot() should be(new Bullet(basicTank.tankData.bulletSpeed, false) with PositionBehaviour(basicTank.positionX + basicTank.directionX,
                                    basicTank.positionY + basicTank.directionY) with DirectionBehaviour
                                    with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                                    Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer)))
        fastTank.shoot() should be(new Bullet(fastTank.tankData.bulletSpeed, false) with PositionBehaviour(fastTank.positionX + fastTank.directionX,
                                    fastTank.positionY + fastTank.directionY) with DirectionBehaviour
                                    with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                                    Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer)))

    }

    it should "have the same speed as the tank that shot it" in {
        basicBullet.speed should equal(basicTank.tankData.bulletSpeed)
        fastBullet.speed should equal(fastTank.tankData.bulletSpeed)
    }


    it should "continue to move in one direction once shot" in {
        basicBullet.positionChanged.apply(basicBullet.positionX + (basicBullet.directionX * basicBullet.speed),
                                    basicBullet.positionY + (basicBullet.directionY * basicBullet.speed))
        basicBullet.positionX should equal((basicTank.positionX + basicTank.directionX) * 3)
        basicBullet.positionY should equal((basicTank.positionY + basicTank.directionY) * 3)
    }
}

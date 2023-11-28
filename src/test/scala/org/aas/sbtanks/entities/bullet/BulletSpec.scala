package org.aas.sbtanks.entities.bullet

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DirectionBehaviour, PositionBehaviour, ConstrainedMovementBehaviour}
import org.aas.sbtanks.entities.tank.behaviours.TankShootingBehaviour
import org.aas.sbtanks.entities.tank.behaviours.TankMultipleShootingBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class BulletSpec extends AnyFlatSpec with Matchers {
    import org.aas.sbtanks.entities.tank.structure.Tank.{FastTank, BasicTank}
    import org.aas.sbtanks.entities.tank.{TankData, TankDataUpdater}
    import org.aas.sbtanks.entities.tank.structure.Tank.{BasicTank, FastTank}
    import org.aas.sbtanks.entities.tank.structure.Tank


    val basicTank = new BasicTank() with PositionBehaviour() with DirectionBehaviour  with TankMultipleShootingBehaviour()
    val fastTank = new FastTank() with PositionBehaviour() with DirectionBehaviour with TankMultipleShootingBehaviour()
    val basicBullet = basicTank.shoot(1, false)
    val fastBullet = fastTank.shoot(2, false)

    "a bullet" should "be created when a tank shoots" in {
        basicTank.shoot(1, false) should be(Seq(new Bullet(basicTank.tankData.bulletSpeed, false) with PositionBehaviour(basicTank.positionX + basicTank.directionX,
                                    basicTank.positionY + basicTank.directionY) with ConstrainedMovementBehaviour with DirectionBehaviour
                                    with CollisionBehaviour(0.5, 0.5, CollisionLayer.BulletsLayer,
                                    Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer, CollisionLayer.NonWalkableLayer))))
        fastTank.shoot(2, false) should be(Seq(new Bullet(fastTank.tankData.bulletSpeed, false)
                                    with PositionBehaviour(fastTank.positionX + fastTank.directionX,
                                    fastTank.positionY + fastTank.directionY) with ConstrainedMovementBehaviour with DirectionBehaviour
                                    with CollisionBehaviour(0.5, 0.5, CollisionLayer.BulletsLayer,
                                    Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer, CollisionLayer.NonWalkableLayer)),
                                    new Bullet(fastTank.tankData.bulletSpeed, false)
                                    with PositionBehaviour(fastTank.positionX + (fastTank.directionX * 2),
                                    fastTank.positionY + (fastTank.directionY * 2)) with ConstrainedMovementBehaviour with DirectionBehaviour
                                    with CollisionBehaviour(0.5, 0.5, CollisionLayer.BulletsLayer,
                                    Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer, CollisionLayer.NonWalkableLayer))))

    }

    it should "have the same speed as the tank that shot it" in {
        basicBullet.head.speed should equal(basicTank.tankData.bulletSpeed)
        fastBullet.head.speed should equal(fastTank.tankData.bulletSpeed)
    }


    it should "continue to move in one direction once shot" in {
        basicBullet(0).moveRelative(basicBullet(0).positionX + (basicBullet(0).directionX * basicBullet(0).speed),
            basicBullet(0).positionY + (basicBullet(0).directionY * basicBullet(0).speed))
        basicBullet(0).moveRelative(basicBullet(0).positionX + (basicBullet(0).directionX * basicBullet(0).speed),
            basicBullet(0).positionY + (basicBullet(0).directionY * basicBullet(0).speed))
        basicBullet(0).positionX should equal(basicTank.positionX + (basicTank.directionX * basicTank.tankData.bulletSpeed) * 2)
        basicBullet(0).positionY should equal(basicTank.positionY + (basicTank.directionY * basicTank.tankData.bulletSpeed) * 2)
    }

    "bullets shot from the same tank" should "move in the same direction" in {
        fastBullet(0).moveRelative(fastBullet(0).positionX + (fastBullet(0).directionX * fastBullet(0).speed),
            fastBullet(0).positionY + (fastBullet(0).directionY * fastBullet(0).speed))
        fastBullet(1).moveRelative(fastBullet(1).positionX + (fastBullet(1).directionX * fastBullet(1).speed),
            fastBullet(1).positionY + (fastBullet(1).directionY * fastBullet(1).speed))
        fastBullet(1).positionX should equal (fastBullet(0).positionX + (fastBullet(0).directionX * fastBullet(0).speed))
        fastBullet(1).positionY should equal (fastBullet(0).positionY + (fastBullet(0).directionY * fastBullet(0).speed))
    }
}

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


    val basicTank = new BasicTank() with TankShootingBehaviour()
    val fastTank = new FastTank()
    val basicBullet = basicTank.shoot()

    "a bullet" should "be created when a tank shoots" in {
        basicTank.shoot() should be(new Bullet() with PositionBehaviour() with DirectionBehaviour
                                    with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                                    Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer)))
    }

    it should "have the same speed as the tank that shot it" in {
        basicBullet should equal(basicTank.tankData.speed)
    }


    it should "continue to move in one direction once shot" in {
        //basicBullet.move()
        //basicBullet.move()
        //basicBullet.position should equal((directionOfBullet.x + 2, directionOfBullet.y))
    }


    it should "stop existing when it collides with something" in {

    }
}

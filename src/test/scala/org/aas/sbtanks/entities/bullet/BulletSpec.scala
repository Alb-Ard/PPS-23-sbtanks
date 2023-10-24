package org.aas.sbtanks.entities.bullet

import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.entities.tank.behaviours.TankShootingBehaviour
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class BulletSpec extends AnyFlatSpec with Matchers {
    import org.aas.sbtanks.entities.tank.structure.Tank.{FastTank, BasicTank}
    import org.aas.sbtanks.entities.tank.{TankData, TankDataUpdater}
    import org.aas.sbtanks.entities.tank.factories.TankTypeData


    val basicTank = new BasicTank() with TankShootingBehaviour(BasicTank().tankData.speed,(1, 0),  0, 0)
    val fastTank = new FastTank()
    val basicBullet = basicTank.shoot()
    val directionOfBullet = basicBullet.position

    "a bullet" should "be created when a tank shoots" in {
        basicTank.shoot() should be(new Bullet(basicTank.tankData.speed, (1, 0), (0, 0)))
    }

    it should "have the same speed as the tank that shot it" in {
        basicBullet.speed should equal(basicTank.tankData.speed)
    }


    it should "continue to move in one direction once shot" in {
        //basicBullet.move()
        //basicBullet.move()
        //basicBullet.position should equal((directionOfBullet.x + 2, directionOfBullet.y))
    }


    it should "stop existing when it collides with something" in {

    }
}

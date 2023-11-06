package org.aas.sbtanks.entities.bullet.behaviour

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.tank.behaviours.TankShootingBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
class BulletCollisionSpec extends AnyFlatSpec with Matchers{
    import org.aas.sbtanks.entities.bullet.Bullet

    val bullet1 = new Bullet(1, false) with PositionBehaviour with DirectionBehaviour
                        with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                        Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
                        with DamageableBehaviour:
                            override def damage(): Unit = -1
    val bullet2 = new Bullet(1, false) with PositionBehaviour with DirectionBehaviour
                        with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                        Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
                        with DamageableBehaviour:
                            override def damage(): Unit = -1

    "a bullet" should "be destroyed when hitting another bullet" in{
        bullet1.positionChanged(bullet1.positionX + (bullet1.directionX * bullet1.speed),
            bullet1.positionY + (bullet1.directionY * bullet1.speed))
        bullet2.positionChanged(bullet2.positionX + (bullet2.directionX * bullet2.speed),
            bullet2.positionY + (bullet2.directionY * bullet2.speed))
    }
}

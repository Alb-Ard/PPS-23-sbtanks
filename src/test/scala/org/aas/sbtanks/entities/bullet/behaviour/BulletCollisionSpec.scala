package org.aas.sbtanks.entities.bullet.behaviour

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.bullet.controller.BulletController
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
                            override def damage(): Unit = this.destroyed(())
    val bullet2 = new Bullet(1, true) with PositionBehaviour(1,0) with DirectionBehaviour
                        with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                        Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
                        with DamageableBehaviour:
                            override def damage(): Unit = this.destroyed(())

    val bulletController = new BulletController(bullet1)

    val destroyedEvent = bullet1.destroyed(())

    "a bullet" should "be destroyed when hitting another bullet" in{
        bulletController.step(1.0) should have(destroyedEvent)
    }
}

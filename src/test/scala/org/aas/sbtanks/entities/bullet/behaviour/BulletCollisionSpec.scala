package org.aas.sbtanks.entities.bullet.behaviour

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour, ConstrainedMovementBehaviour, MovementBehaviour}
import org.aas.sbtanks.physics.CollisionLayer
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
class BulletCollisionSpec extends AnyFlatSpec with Matchers{
    import org.aas.sbtanks.entities.bullet.Bullet
    import org.aas.sbtanks.entities.tank.structure.Tank
    import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
    import org.aas.sbtanks.entities.tank.behaviours.TankShootingBehaviour
    import org.aas.sbtanks.entities.bullet.controller.BulletController

    val tank: Tank = new BasicTank() with PositionBehaviour() with DirectionBehaviour with MovementBehaviour
        with TankShootingBehaviour() with DamageableBehaviour:
            override def applyDamage(amount: Int)= {
                tankData.updateHealth(_ - 1)
                if (tankData.health == 0)
                    this.destroyed(())
                this
            }

    val bullet1 = new Bullet(1, false) with PositionBehaviour with ConstrainedMovementBehaviour with DirectionBehaviour
                        with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                        Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
                        with DamageableBehaviour:
                            override def applyDamage(amount: Int) =
                                destroyed(())
                                this
    val bullet2 = new Bullet(1, true) with PositionBehaviour(1,0) with ConstrainedMovementBehaviour
                        with DirectionBehaviour with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                        Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
                        with DamageableBehaviour:
                            override def applyDamage(amount: Int) =
                                destroyed(())
                                this

    val bullet3 = new Bullet(1, true) with PositionBehaviour with ConstrainedMovementBehaviour with DirectionBehaviour
                        with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                        Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
                        with DamageableBehaviour:
                            override def applyDamage(amount: Int) =
                                destroyed(())
                                this

    val bulletController = new BulletController(bullet1)

    "a bullet" should "be destroyed when it collides with something" in {
        var wasDestroyed = false
        bullet1.destroyed += { _ => wasDestroyed = true }
        bulletController.step(1.0)
        wasDestroyed should be (true)
    }

    "a bullet" should "damage a tank when it collides with it" in {
        bulletController.step(0.0)
    }
}

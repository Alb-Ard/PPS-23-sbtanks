package org.aas.sbtanks.entities.bullet.behaviour

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.physics.PhysicsWorld
import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.bullet.controller.BulletController
import org.aas.sbtanks.entities.bullet.view.BulletView
import org.aas.sbtanks.entities.bullet.view.scalafx.JFXBulletView
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.aas.sbtanks.entities.tank.behaviours.TankShootingBehaviour
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import scalafx.scene.image.Image

class BulletCollisionSpec extends AnyFlatSpec with Matchers {

    val viewScale = 4D
    val tileSize = 16D
    val bulletView = new Image(JFXImageLoader.loadFromResources("entities/bullet/bullet.png", tileSize, viewScale))

    class MockBullet(override val speed: Double, override val isPlayerBullet: Boolean) extends Bullet(speed, isPlayerBullet)
        with PositionBehaviour
        with MovementBehaviour
        with DirectionBehaviour
        with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
            Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
        with DamageableBehaviour:

        override def applyDamage(amount: Int) = 
            destroyed(())
            this
        
    abstract class MockTank(startingX: Double, startingY: Double) extends BasicTank()
        with PositionBehaviour(startingX, startingY)
        with DirectionBehaviour
        with MovementBehaviour
        with CollisionBehaviour(1, 1, CollisionLayer.TanksLayer, Seq(CollisionLayer.BulletsLayer))
        with DamageableBehaviour

    "A bullet" should "be destroyed when it collides with something" in {
        PhysicsWorld.clearColliders()
        val tank = new MockTank(0, 2):
            override def applyDamage(amount: Int) = this
        PhysicsWorld.registerCollider(tank)
        val bullet = new MockBullet(1, false)
        PhysicsWorld.registerCollider(bullet)
        bullet.setDirection(0, 1)
        val bulletController = new BulletController(bullet, new JFXBulletView(bulletView))
        var wasDestroyed = false
        bullet.destroyed += { _ => wasDestroyed = true }
        for _ <- 0 until 10 do bulletController.step(1.0)
        wasDestroyed should be (true)
    }

    it should "damage a tank when it collides with it" in {
        PhysicsWorld.clearColliders()
        var wasTankDamaged = false
        val tank = new MockTank(0, 2):
            override def applyDamage(amount: Int) = {
                wasTankDamaged = true
                updateTankData(tankData.updateHealth(_ - 1))
                if (tankData.health <= 0)
                    destroyed(())
                this
            }
        PhysicsWorld.registerCollider(tank)
        val bullet = new MockBullet(1, false)
        PhysicsWorld.registerCollider(bullet)
        bullet.setDirection(0, 1)
        val bulletController = new BulletController(bullet, new JFXBulletView(bulletView))
        for _ <- 0 until 10 do bulletController.step(1.0)
        wasTankDamaged should be (true)
    }
}

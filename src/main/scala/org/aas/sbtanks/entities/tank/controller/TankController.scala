package org.aas.sbtanks.entities.tank.controller

import org.aas.sbtanks.behaviours.DirectionBehaviour
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.common.view.DirectionableView.lookInDirection
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.structure.Tank
import TankController.ControllableTank
import org.aas.sbtanks.entities.tank.behaviours.TankMultipleShootingBehaviour
import org.aas.sbtanks.behaviours.DamageableBehaviour
import reflect.Selectable.reflectiveSelectable
import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.entities.bullet.controller.BulletController.CompleteBullet
import org.aas.sbtanks.entities.bullet.view.scalafx.JFXBulletView
import org.aas.sbtanks.resources.scalafx.JFXMediaPlayer
import org.aas.sbtanks.physics.PhysicsContainer
import org.aas.sbtanks.resources.scalafx.JFXImageLoader

type TankControllerMoveSound = {
    def play(): Unit
    def stop(): Unit
}

trait TankController[S <: TankControllerMoveSound](using PhysicsContainer)(tank: ControllableTank, tankView: TankView, viewScale: Double, tileSize: Double, protected val moveSound: Option[S]) extends Steppable:
    private val SHOOT_DELAY_AMOUNT = 0.2D

    val bulletShot = EventSource[(CompleteBullet, JFXBulletView)]

    private var shootDelay = 0.0

    tank.directionChanged += { (x, y) => 
        tankView.lookInDirection(x, y)
        tankView.isMoving(x != 0 || y != 0)
        if x == 0 && y == 0 then
            moveSound.foreach(_.stop())
        else
            moveSound.foreach(_.play())
    }
    tank.positionChanged += { (x, y) => 
        tankView.move(x * viewScale * tileSize, y * viewScale * tileSize)
    }
    tank.damageableChanged += tankView.isDamageable
    tankView.isDamageable(tank.isDamageable)
    tank.setDirection(0, 1)

    override def step(delta: Double) = 
        shootDelay += delta
        this

    protected def shoot() = 
        if shootDelay >= SHOOT_DELAY_AMOUNT then
            val bullet = tank.shoot(isPlayerBullet = true)
                .map(b => (
                    b,
                    new JFXBulletView(JFXImageLoader.loadFromResources("entities/bullet/bullet.png", tileSize, viewScale)),
                ))
                .foreach((b, v) =>
                    v.move(b.positionX * tileSize * viewScale, b.positionY * tileSize * viewScale)
                    bulletShot(b, v)
                )
            JFXMediaPlayer.play(JFXMediaPlayer.BULLET_SFX)
            shootDelay = 0
        this

object TankController:    
    type ControllableTank = Tank
        with DirectionBehaviour
        with MovementBehaviour
        with PositionBehaviour
        with CollisionBehaviour
        with TankMultipleShootingBehaviour
        with DamageableBehaviour

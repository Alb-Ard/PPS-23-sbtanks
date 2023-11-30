package org.aas.sbtanks.entities.bullet.controller

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.behaviours.DamageableBehaviour.damage
import org.aas.sbtanks.entities.bullet.view.BulletView
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.entities.bullet.controller.BulletController.CompleteBullet

class BulletController(bullet: CompleteBullet, bulletView: BulletView, speedMultiplier: Double, viewScale: Double, tileSize: Double) extends Steppable:

    bullet.overlapping += checkCollision
    bullet.positionChanged += { (x, y) => bulletView.move(x * viewScale * tileSize, y * viewScale * tileSize) }
    bulletView.lookInDirection(bullet.directionX, bullet.directionY)

    override def step(delta: Double) =
        bullet.moveRelative(bullet.directionX * bullet.speed * speedMultiplier, bullet.directionY * bullet.speed * speedMultiplier)
        this

    private def checkCollision(colliders: Seq[Collider]): Unit =
        if (colliders.map(c => c match
            case el: Tank with DamageableBehaviour => {
                if(checkBulletPlayer(el))
                    el.damage()
                    true
                else
                    false
            }
            case el: DamageableBehaviour => {
                el.damage()
                true
            }
            case el => {
                true
            } 
        ).contains(true))
            bullet.damage()

    private def checkBulletPlayer(tank: Tank): Boolean =
        (bullet.isPlayerBullet && !tank.isInstanceOf[PlayerTank]) || (!bullet.isPlayerBullet && tank.isInstanceOf[PlayerTank])

object BulletController:
    type CompleteBullet = Bullet
        with PositionBehaviour
        with MovementBehaviour
        with DirectionBehaviour
        with CollisionBehaviour
        with DamageableBehaviour
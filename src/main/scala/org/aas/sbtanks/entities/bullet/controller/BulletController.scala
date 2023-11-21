package org.aas.sbtanks.entities.bullet.controller

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.behaviours.DamageableBehaviour.damage
import org.aas.sbtanks.entities.bullet.view.BulletView
import org.aas.sbtanks.player.PlayerTank

class BulletController(bullet: Bullet with PositionBehaviour with MovementBehaviour
                        with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour, bulletView: BulletView) extends Steppable:

    bullet.overlapping += checkCollision
    bullet.positionChanged += bulletView.move

    override def step(delta: Double) =
        bullet.moveRelative(bullet.directionX * bullet.speed, bullet.directionY * bullet.speed)
        println("Bullet Speed: " + bullet.speed)
        println("Bullet Position: (" + bullet.positionX + ", " + bullet.positionY + ")")
        println("Bullet Direction: (" + bullet.directionX + ", " + bullet.directionY + ")")
        this

    private def checkCollision(colliders: Seq[Collider]): Unit =
        colliders.foreach(c => c match
            case el: Tank with DamageableBehaviour => {
                if(checkBulletPlayer(el))
                    el.damage()
            }
            case el: DamageableBehaviour => {
                el.damage()
            }
            case el => el
        )
        bullet.damage()

    private def checkBulletPlayer(tank: Tank): Boolean =
        (bullet.isPlayerBullet && !tank.isInstanceOf[PlayerTank]) || (!bullet.isPlayerBullet && tank.isInstanceOf[PlayerTank])

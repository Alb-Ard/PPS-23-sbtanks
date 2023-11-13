package org.aas.sbtanks.entities.bullet.controller

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.{Collider, CollisionLayer}
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.behaviours.DamageableBehaviour.damage
import org.aas.sbtanks.behaviours.MovementBehaviour

class BulletController(bullet: Bullet with PositionBehaviour with MovementBehaviour
                        with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour) extends Steppable:

    bullet.overlapping += checkCollision

    override def step(delta: Double) =
        bullet.moveRelative(bullet.directionX * bullet.speed, bullet.directionY * bullet.speed)
        this

    //differenze tra bullet tank e bullet player:
    //bullet tank colpisce altri bullets di tutti i tipi, ostacoli e il player
    //bullet player colpisce gli altri tank, gli ostacoli e i proiettili di tutti i tipi
    private def checkCollision(colliders: Seq[Collider]): Unit =
        colliders.foreach(c => c match
            case el: Tank with DamageableBehaviour => {
                if(checkBulletPlayer(el))
                    el.damage()
            }
            case el: DamageableBehaviour => {
                el.damage()
            }
        )
        bullet.damage()

    private def checkBulletPlayer(tank: Tank): Boolean =
        //(bullet.isPlayerBullet && !tank.isPlayer) || (!bullet.isPlayerBullet && tank.isPlayer)
        //NEED ISPLAYER FLAG
        true

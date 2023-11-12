package org.aas.sbtanks.entities.bullet.controller

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.{Collider, CollisionLayer}
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.DestroyableEntityAutoManager
import org.aas.sbtanks.behaviours.DamageableBehaviour.damage

class BulletController(bullet: Bullet with PositionBehaviour with ConstrainedMovementBehaviour
                        with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour) extends Steppable:

    bullet.overlapping += checkCollision

    override def step(delta: Double) =
        bullet.moveRelative(bullet.positionX + (bullet.directionX * bullet.speed),
            bullet.positionY + (bullet.directionY * bullet.speed))
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

        //:: Nil
        /*
        if(colliders.contains(CollisionLayer.TanksLayer))
            val hitTank = colliders.find(el => el.layer == CollisionLayer.TanksLayer).get.asInstanceOf[Tank with DamageableBehaviour]
            if(checkBulletPlayer(hitTank))
                hitTank.damage()
                //if(hitTank.isPlayer && hitTank.tankData.health == 0)
                    //respawn()
        if(colliders.contains(CollisionLayer.WallsLayer))
            colliders.find(el => el.layer == CollisionLayer.WallsLayer).get
                .asInstanceOf[LevelObstacle with DamageableBehaviour].damage()
        if(colliders.contains(CollisionLayer.BulletsLayer))
            bullet.damage()
            colliders.find(el => el.layer == CollisionLayer.BulletsLayer).get
                .asInstanceOf[Bullet with DamageableBehaviour].damage()

         */

    private def checkBulletPlayer(tank: Tank): Boolean =
        //(bullet.isPlayerBullet && !tank.isPlayer) || (!bullet.isPlayerBullet && tank.isPlayer)
        true

    /*
    private def getColliders(): List[Collider] =
        bullet.overlappedColliders.filter(el => el.layer == CollisionLayer.BulletsLayer ||
            el.layer == CollisionLayer.WallsLayer || el.layer == CollisionLayer.TanksLayer)

    */
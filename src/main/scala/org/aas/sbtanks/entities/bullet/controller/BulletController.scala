package org.aas.sbtanks.entities.bullet.controller

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.behaviours.{CollisionBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.{Collider, CollisionLayer}
import org.aas.sbtanks.common.Steppable

trait BulletController(bullet: Bullet with PositionBehaviour with DirectionBehaviour with CollisionBehaviour) extends Steppable :

    override def step(delta: Double): Steppable =
        checkCollision()
        bullet.positionChanged((bullet.positionX + (bullet.directionX * bullet.speed),
            bullet.positionY + (bullet.directionY * bullet.speed)))
        this

    //differenze tra bullet tank e bullet player:
    //bullet tank colpisce altri bullets di tutti i tipi, ostacoli e il player
    //bullet player colpisce gli altri tank, gli ostacoli e i proiettili di tutti i tipi
    private def checkCollision(): Unit =
        if(bullet.overlapsAnything) then
            val collider = bullet.overlappedColliders.filter(el => el.layer == CollisionLayer.BulletsLayer ||
                                                el.layer == CollisionLayer.WallsLayer || el.layer == CollisionLayer.TanksLayer)
            //if(collider.length == 1 && collider.contains(CollisionLayer.BulletsLayer))


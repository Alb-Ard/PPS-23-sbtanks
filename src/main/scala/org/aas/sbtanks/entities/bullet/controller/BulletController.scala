package org.aas.sbtanks.entities.bullet.controller

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.behaviours.{CollisionBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.{Collider, CollisionLayer, PhysicsWorld}
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.tank.{TankData, TankDataUpdater}

trait BulletController(bullet: Bullet with PositionBehaviour with DirectionBehaviour with CollisionBehaviour) extends Steppable :

    override def step(delta: Double): Steppable =
        checkCollision() //non serve passare un collider perchè il Bullet è un Collider!
        bullet.positionChanged((bullet.positionX + (bullet.directionX * bullet.speed),
            bullet.positionY + (bullet.directionY * bullet.speed)))
        this

    //differenze tra bullet tank e bullet player:
    //bullet tank colpisce altri bullets di tutti i tipi, ostacoli e il player
    //bullet player colpisce gli altri tank, gli ostacoli e i proiettili di tutti i tipi
    private def checkCollision(): Unit =
        if(bullet.overlapsAnything)
            val collider = bullet.overlappedColliders.filter(el => el.layer == CollisionLayer.BulletsLayer ||
                                                el.layer == CollisionLayer.WallsLayer || el.layer == CollisionLayer.TanksLayer)
            if(collider.contains(CollisionLayer.TanksLayer))
                val hitEnemy: Tank = collider.find(el => el.layer == CollisionLayer.TanksLayer).get.asInstanceOf[Tank]
                hitEnemy.updateTankData(hitEnemy.tankData.updateHealth(_ - 1))
            if(collider.contains(CollisionLayer.WallsLayer))
                val hitWall = collider.find(el => el.layer == CollisionLayer.WallsLayer).get.asInstanceOf[LevelObstacle]
            if(collider.contains(CollisionLayer.BulletsLayer))
                val hitBullet: Bullet = collider.find(el => el.layer == CollisionLayer.BulletsLayer).get.asInstanceOf[Bullet]
                //elimina qui il proiettile con cui mi sono scontrato
            //PhysicsWorld.unregisterCollider(bullet)



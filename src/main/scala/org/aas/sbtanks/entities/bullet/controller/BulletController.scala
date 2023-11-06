package org.aas.sbtanks.entities.bullet.controller

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.{Collider, CollisionLayer}
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.DestroyableEntityAutoManager

trait BulletController(bullet: Bullet with PositionBehaviour with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour) extends Steppable:

    override def step(delta: Double): BulletController.this.type =
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
                val hitEnemy = collider.find(el => el.layer == CollisionLayer.TanksLayer).get.asInstanceOf[Tank with DamageableBehaviour]
                hitEnemy.damage()
                if(hitEnemy.tankData.health <= 0)
                    hitEnemy.destroyed.apply(hitEnemy)
                //hitEnemy.updateTankData(hitEnemy.tankData.updateHealth(_ - 1))
            if(collider.contains(CollisionLayer.WallsLayer))
                val hitWall = collider.find(el => el.layer == CollisionLayer.WallsLayer).get.asInstanceOf[LevelObstacle with DamageableBehaviour]
                hitWall.destroyed.apply(hitWall)
            if(collider.contains(CollisionLayer.BulletsLayer))
                val hitBullet= collider.find(el => el.layer == CollisionLayer.BulletsLayer).get.asInstanceOf[Bullet with DamageableBehaviour]
                bullet.destroyed.apply(bullet)
                hitBullet.destroyed.apply(hitBullet)
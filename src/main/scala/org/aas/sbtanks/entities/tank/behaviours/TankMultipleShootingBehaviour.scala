package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.entities.bullet.controller.BulletController.CompleteBullet

trait TankMultipleShootingBehaviour:
    this: Tank with PositionBehaviour with DirectionBehaviour =>

    val BULLET_OFFSET = 0.1D
    
    private val BULLET_COLLISION_SIZE = 0.25D
    private val BULLET_COLLISION_OFFSET = (1D - BULLET_COLLISION_SIZE) / 2D

    def shoot(nShots: Int, isPlayerBullet: Boolean) =
        var shotsFired: Seq[CompleteBullet] = Seq.empty
        for(n <- Range(1, nShots + 1))
            shotsFired = shotsFired :+ generateBullet(n, isPlayerBullet)
        shotsFired

    private def generateBullet(index: Int, isPlayerBullet: Boolean) =
        val direction = (lastValidDirectionX.getOrElse(0D), lastValidDirectionY.getOrElse(1D))
        val offset = (direction(0) * index * BULLET_OFFSET, direction(1) * index * BULLET_OFFSET)
        new Bullet(tankData.bulletSpeed, isPlayerBullet)
            with PositionBehaviour(positionX + offset(0), positionY + offset(1))
            with MovementBehaviour
            with DirectionBehaviour(direction(0), direction(1))
            with CollisionBehaviour(BULLET_COLLISION_SIZE, BULLET_COLLISION_SIZE, CollisionLayer.BulletsLayer,
                Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
            with DamageableBehaviour:
                override def applyDamage(amount: Int) =
                    destroyed(())
                    this
        .setOffset(BULLET_COLLISION_OFFSET, BULLET_COLLISION_OFFSET)
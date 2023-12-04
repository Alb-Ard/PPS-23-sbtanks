package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.entities.bullet.controller.BulletController.CompleteBullet

/**
 * Trait used as behaviour for enemy and player tanks to shoot various amounts of bullets at a time.
 */
trait TankMultipleShootingBehaviour:
    this: Tank with PositionBehaviour with DirectionBehaviour =>

    private val BULLET_OFFSET = 0.1D
    private val BULLET_COLLISION_SIZE = 0.25D
    private val BULLET_COLLISION_OFFSET = (1D - BULLET_COLLISION_SIZE) / 2D

    /**
     * method that generates a sequence of n bullets that the tank will shoot. It uses method generateBullet to create
     * its sequence.
     *
     * @param nShots the number of shots that will be fired.
     * @param isPlayerBullet a flag to determine whether the player or an enemy tank will shot these bullets.
     * @return a sequence of shot bullets.
     */
    def shoot(nShots: Int, isPlayerBullet: Boolean) =
        var shotsFired: Seq[CompleteBullet] = Seq.empty
        for(n <- Range(1, nShots + 1))
            shotsFired = shotsFired :+ generateBullet(n, isPlayerBullet)
        shotsFired

    /**
     * this private def generates one complete bullet, with position, movement, direction, collision and damageable behaviours.
     *
     * @param index index value that shows the n-th bullet that will be shot. This value is used to determine offset from one bullet to another.
     * @param isPlayerBullet a flag to determine whether the player or an enemy tank will shot these bullets.
     * @return a complete bullet.
     */
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
package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.entities.bullet.controller.BulletController.CompleteBullet
import org.aas.sbtanks.entities.bullet.BulletFactory
import org.aas.sbtanks.entities.powerups.effects.a.tank

/**
 * Trait used as behaviour for enemy and player tanks to shoot various amounts of bullets at a time.
 */
trait TankMultipleShootingBehaviour:
    this: Tank with PositionBehaviour with DirectionBehaviour =>

    private val BULLET_OFFSET = 0.1D

    private val bulletFactory = BulletFactory()

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
     * Generates one complete bullet, with this tank position and direction.
     *
     * @param index index value that shows the n-th bullet that will be shot. This value is used to determine offset from one bullet to another.
     * @param isPlayerBullet a flag to determine whether the player or an enemy tank will shot these bullets.
     * @return The crated bullet.
     */
    private def generateBullet(index: Int, isPlayerBullet: Boolean) =
        val direction = (lastValidDirectionX.getOrElse(0D), lastValidDirectionY.getOrElse(1D))
        val offset = (direction(0) * index * BULLET_OFFSET, direction(1) * index * BULLET_OFFSET)
        bulletFactory.create((positionX + offset(0), positionY + offset(1)), direction, tankData.bulletSpeed, isPlayerBullet)
package org.aas.sbtanks.entities.bullet

import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.behaviours.DirectionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.physics.PhysicsContainer

/**
  * A factory that creates tank bullets.
  */
class BulletFactory:
    private type Position = (Double, Double)
    private type Direction = (Double, Double)

    private val BULLET_COLLISION_SIZE = (0.25D, 0.25D)
    private val BULLET_COLLISION_OFFSET = (getOffsetFromSize(BULLET_COLLISION_SIZE(0)), getOffsetFromSize(BULLET_COLLISION_SIZE(1)))
    private val BULLET_COLLISION_MASK = Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer)

    /**
     * Generates one complete bullet, with position, movement, direction, collision and damageable behaviours.
     *
     * @param position The starting position of the bullet.
     * @param direction The shoot direction of the bullet.
     * @param speed The bullet speed.
     * @param isPlayerBullet A flag to determine whether the player or an enemy tank will shot these bullets.
     * @return The created bullet.
     */
    def create(using physics: PhysicsContainer)(position: Position, direction: Direction, speed: Double, isPlayerBullet: Boolean) =
        new Bullet(speed, isPlayerBullet)
            with PositionBehaviour(position(0), position(1))
            with MovementBehaviour
            with DirectionBehaviour(direction(0), direction(1))
            with CollisionBehaviour(BULLET_COLLISION_SIZE(0), BULLET_COLLISION_SIZE(1), CollisionLayer.BulletsLayer, BULLET_COLLISION_MASK)
            with DamageableBehaviour:
                override def applyDamage(source: Any, amount: Int) =
                    destroy(source)
                    this
        .setBoundingBoxOffset(BULLET_COLLISION_OFFSET(0), BULLET_COLLISION_OFFSET(1))

    private def getOffsetFromSize(size: Double) =
        (1D - size) / 2D
        
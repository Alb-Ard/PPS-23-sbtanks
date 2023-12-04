package org.aas.sbtanks.entities.bullet.controller

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.behaviours.DamageableBehaviour.damage
import org.aas.sbtanks.entities.bullet.view.BulletView
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.entities.bullet.controller.BulletController.CompleteBullet
import org.aas.sbtanks.physics.PhysicsWorld
import org.aas.sbtanks.physics.AABB

/**
 * This is a controller used to determine interactions and behaviours of Bullets. It takes data of bullet, making it move
 * while checking for possible collisions with other entities that are part of the Collision layer mask. If it collides, it
 * calls for a destroyed event to eliminate it from the entities repository.
 *
 * @param bullet a complete bullet's model that is moving
 * @param bulletView a view of said bullet
 * @param speedMultiplier a double value that determines the speed of the bullet in View
 * @param viewScale the scale of the view
 * @param tileSize the size of each tile of the view
 */
class BulletController(bullet: CompleteBullet, bulletView: BulletView, speedMultiplier: Double, viewScale: Double, tileSize: Double) extends Steppable:
    private val EXPLOSION_MUTLIPLIER = 3.5D

    bullet.overlapping += checkCollisions
    bullet.positionChanged += { (x, y) => bulletView.move(x * viewScale * tileSize, y * viewScale * tileSize) }
    bulletView.move(bullet.positionX * viewScale * tileSize, bullet.positionY * viewScale * tileSize)
    bulletView.lookInDirection(bullet.directionX, bullet.directionY)

    override def step(delta: Double) =
        bullet.moveRelative(bullet.directionX * bullet.speed * speedMultiplier, bullet.directionY * bullet.speed * speedMultiplier)
        this

    /**
     * Manages a collision with other colliders and, if needed, destroys the controlled bullet.
     *
     * @param colliders The Seq of the hit colliders.
     */
    private def checkCollisions(colliders: Seq[Collider]): Unit =
        if (addAllCollidersInRange(colliders).map(handleCollision).contains(true))
            bullet.damage()

    /**
      * Finds all the colliders in the bullets explosion range given the actually hit colliders .
      *
      * @param colliders The hit colliders.
      * @return A Set with the hit colliders joined with the colliders in the explosion range.
      */
    private def addAllCollidersInRange(colliders: Seq[Collider]) =
        colliders.flatMap:
                case o: LevelObstacle with PositionBehaviour with CollisionBehaviour => findObstaclesInRange(o)
                case e => Seq(e)
            .toSet

    /**
     * Handles the collision of the controlled bullet with another collider, and returns true if the collision was succesfull.
     *
     * @param collider The hit collider.
     * @return true if bullet has collided, otherwise false.
     */
    private def handleCollision(collider: Collider) = collider match
        case tank: Tank with DamageableBehaviour =>
            if(checkBulletPlayer(tank))
                tank.damage()
                true
            else
                false
        case obstacle: LevelObstacle with DamageableBehaviour =>
            obstacle.damage()
            true
        case damageable: DamageableBehaviour =>
            damageable.damage()
            true
        case c => true

    private def findObstaclesInRange(hitObstacle: LevelObstacle with PositionBehaviour with CollisionBehaviour) =
        val bulletBox = bullet.boundingBox
        val explosionBox = Math.abs(bullet.directionY) > Math.abs(bullet.directionX) match
            case true => AABB(bulletBox.x - (bulletBox.width * EXPLOSION_MUTLIPLIER - bulletBox.width) / 2D, bulletBox.y, bulletBox.width * EXPLOSION_MUTLIPLIER, bulletBox.height)
            case _ => AABB(bulletBox.x, bulletBox.y - (bulletBox.height * EXPLOSION_MUTLIPLIER - bulletBox.height) / 2D, bulletBox.width, bulletBox.height * EXPLOSION_MUTLIPLIER)
        PhysicsWorld.getBoxOverlaps(explosionBox, bullet.layerMasks, Seq(bullet))
            .flatMap:
                case c: LevelObstacle with DamageableBehaviour => Option(c)
                case _ => Option.empty
        

    /**
     * private def used to check if the bullet was shot by a player and is colliding with an enemy tank or viceversa.
     *
     * @param tank the tank that the bullet is colliding with.
     * @return boolean value true if the bullet was shot by a player and the hit tank was an enemy or viceversa, otherwise false.
     */
    private def checkBulletPlayer(tank: Tank): Boolean =
        (bullet.isPlayerBullet && !tank.isInstanceOf[PlayerTank]) || (!bullet.isPlayerBullet && tank.isInstanceOf[PlayerTank])

object BulletController:
    type CompleteBullet = Bullet
        with PositionBehaviour
        with MovementBehaviour
        with DirectionBehaviour
        with CollisionBehaviour
        with DamageableBehaviour
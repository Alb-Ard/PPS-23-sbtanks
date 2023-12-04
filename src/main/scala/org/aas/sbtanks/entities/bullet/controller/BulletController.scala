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

    bullet.overlapping += checkCollisions
    bullet.positionChanged += { (x, y) => bulletView.move(x * viewScale * tileSize, y * viewScale * tileSize) }
    bulletView.lookInDirection(bullet.directionX, bullet.directionY)

    override def step(delta: Double) =
        bullet.moveRelative(bullet.directionX * bullet.speed * speedMultiplier, bullet.directionY * bullet.speed * speedMultiplier)
        this

    /**
     * this private def is called to check for possible collisions that the bullet may have while traversing the level.
     * It uses private method handleCollision to check whether the bullet has collided with another possible entity of the
     * layer mask, and if the result is true, it calls method damage in bullet.
     *
     * @param colliders a seq of all possible colliders
     */
    private def checkCollisions(colliders: Seq[Collider]): Unit =
        if (colliders.map(handleCollision).contains(true))
            bullet.damage()

    /**
     * this method checks whether the bullet has collided with a tank, a obstacle or another bullet.
     *
     * @param collider a seq of all possible colliders.
     * @return boolean value true if bullet has collided with one of those previously mentioned entities, otherwise false.
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
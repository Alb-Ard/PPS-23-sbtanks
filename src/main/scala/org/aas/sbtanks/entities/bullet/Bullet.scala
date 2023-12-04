package org.aas.sbtanks.entities.bullet

/**
 * Class that represents the bullet's model.
 *
 * @param speed the speed of the bullet
 * @param isPlayerBullet a flag that determines whether the bullet was shot by a player or by an enemy tank.
 */
class Bullet(val speed: Double, val isPlayerBullet: Boolean)

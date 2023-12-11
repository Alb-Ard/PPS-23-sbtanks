package org.aas.sbtanks.entities.tank


/**
 * A case class representing data for a tank (health, speed and bullet speed)
 *
 * @param health      The health of the tank.
 * @param speed       The speed of the tank.
 * @param bulletSpeed The speed of the tank's bullets.
 */
case class TankData(health: Int, speed: Int, bulletSpeed: Int)


/**
 * A trait providing methods to update specific attributes of a tank's data.
 */
trait TankDataUpdater:
    self: TankData =>

    /**
     * Updates the health attribute of the tank data using the provided function.
     *
     * @param f The function to apply to the current health value.
     * @return A new instance of TankData with the updated health value and still supporting data updates.
     */
    def updateHealth(f: Int => Int): TankData with TankDataUpdater = new TankData(f(health), speed, bulletSpeed) with TankDataUpdater

    /**
     * Updates the speed attribute of the tank data using the provided function.
     *
     * @param f The function to apply to the current speed value.
     * @return A new instance of TankData with the updated speed value and still supporting data updates.
     */
    def updateSpeed(f: Int => Int): TankData with TankDataUpdater = new TankData(health, f(speed), bulletSpeed) with TankDataUpdater

    /**
     * Updates the bullet speed attribute of the tank data using the provided function.
     *
     * @param f The function to apply to the current bullet speed value.
     * @return A new instance of TankData with the updated bullet speed value and still supporting data updates.
     */
    def updateBulletSpeed(f: Int => Int): TankData with TankDataUpdater = new TankData(health, speed, f(bulletSpeed)) with TankDataUpdater




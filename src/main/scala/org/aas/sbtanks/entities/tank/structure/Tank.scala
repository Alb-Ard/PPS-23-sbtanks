package org.aas.sbtanks.entities.tank.structure

import org.aas.sbtanks.entities.tank.{TankData, TankDataUpdater}
import org.aas.sbtanks.entities.tank.factories.TankTypeData
import org.aas.sbtanks.entities.tank.factories.BasicTankData
import org.aas.sbtanks.entities.tank.factories.FastTankData
import org.aas.sbtanks.entities.tank.factories.ArmorTankData
import org.aas.sbtanks.entities.tank.factories.PowerTankData


/**
 * A trait representing a tank along with its data.
 *
 * @param dataSupplier A function providing the initial tank data.
 */
trait Tank(dataSupplier: TankTypeData):

    var isCharged = false

    var tankData: TankData & TankDataUpdater = dataSupplier()

    /**
     * Updates the tank data with new data.
     *
     * @param newData The new tank data to apply.
     */
    def updateTankData(newData: TankData & TankDataUpdater): Unit = tankData = newData

    /**
     * Sets the charged status of the tank.
     *
     * @param value The value to set for the charged status.
     * @return The current instance of Tank.
     */
    def setCharged(value: Boolean): this.type =
        this.isCharged = value
        this


/**
 * A companion object for the Tank trait, providing concrete implementations for all of the available typologies of tanks.
 */
object Tank:
    class BasicTank extends Tank(BasicTankData)

    class FastTank extends Tank(FastTankData)

    class ArmorTank extends Tank(ArmorTankData)

    class PowerTank extends Tank(PowerTankData)







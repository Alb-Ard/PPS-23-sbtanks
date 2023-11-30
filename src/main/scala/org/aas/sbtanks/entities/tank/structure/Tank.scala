package org.aas.sbtanks.entities.tank.structure

import org.aas.sbtanks.entities.tank.{TankData, TankDataUpdater}
import org.aas.sbtanks.entities.tank.factories.TankTypeData
import org.aas.sbtanks.entities.tank.factories.BasicTankData
import org.aas.sbtanks.entities.tank.factories.FastTankData
import org.aas.sbtanks.entities.tank.factories.ArmorTankData
import org.aas.sbtanks.entities.tank.factories.PowerTankData


trait Tank(dataSupplier: TankTypeData):

    var isCharged = false

    var tankData: TankData & TankDataUpdater = dataSupplier()

    def updateTankData(newData: TankData & TankDataUpdater): Unit = tankData = newData

    def setCharged(value: Boolean): this.type =
        this.isCharged = value
        this





object Tank:
    class BasicTank extends Tank(BasicTankData)

    class FastTank extends Tank(FastTankData)

    class ArmorTank extends Tank(ArmorTankData)

    class PowerTank extends Tank(PowerTankData)







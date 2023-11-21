package org.aas.sbtanks.entities.tank.structure

import org.aas.sbtanks.entities.tank.{TankData, TankDataUpdater}
import org.aas.sbtanks.entities.tank.factories.TankTypeData
import org.aas.sbtanks.entities.tank.factories.BasicTankData
import org.aas.sbtanks.entities.tank.factories.FastTankData
import org.aas.sbtanks.entities.tank.factories.ArmorTankData
import org.aas.sbtanks.entities.tank.factories.PowerTankData


trait Tank(dataSupplier: TankTypeData):

    def updateTankData(newData: TankData & TankDataUpdater): Unit = tankData = newData

    var tankData: TankData & TankDataUpdater = dataSupplier()





object Tank:
    class BasicTank extends Tank(BasicTankData)

    class FastTank extends Tank(FastTankData)

    class ArmorTank extends Tank(ArmorTankData)

    class PowerTank extends Tank(PowerTankData)

object Test extends App:
    import Tank.*

    val tank = new BasicTank()
    

    println(tank.tankData.health)
    println(tank.tankData.speed)

    tank updateTankData (tank.tankData.updateSpeed(_ * 2)
        .updateHealth(_ + 5))



    println(tank.tankData.health)
    println(tank.tankData.speed)





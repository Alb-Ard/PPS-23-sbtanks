package org.aas.sbtanks.entities.tank.structure

import org.aas.sbtanks.entities.tank.{TankData, TankDataUpdater}
import org.aas.sbtanks.entities.tank.factories.TankTypeData


trait Tank:

    def updateTankData(newData: TankData & TankDataUpdater): Unit = tankData = newData


    var tankData: TankData & TankDataUpdater = tankTypeData(() => tankData)


    def tankTypeData(supplier: () => TankData & TankDataUpdater) : TankData & TankDataUpdater




object Tank extends App:
    import org.aas.sbtanks.entities.tank.factories.TankTypeData.*

    class BasicTank extends Tank:
        override def tankTypeData(supplier: () => TankData & TankDataUpdater) : TankData & TankDataUpdater = BasicTank.supplyData


    class FastTank extends Tank:
        override def tankTypeData(supplier: () => TankData & TankDataUpdater) : TankData & TankDataUpdater = FastTank.supplyData

    class ArmorTank extends Tank:
        override def tankTypeData(supplier: () => TankData & TankDataUpdater) : TankData & TankDataUpdater = ArmorTank.supplyData

    class PowerTank extends Tank:
        override def tankTypeData(supplier: () => TankData & TankDataUpdater) : TankData & TankDataUpdater = PowerTank.supplyData


  //import org.aas.sbtanks.entities.tank.structure.Tank.PowerTank

object Test extends App:
    import Tank.*

    val tank = new BasicTank()

    println(tank.tankData.health)
    println(tank.tankData.speed)

    tank updateTankData (tank.tankData.updateSpeed(_ * 2)
        .updateHealth(_ + 5))



    println(tank.tankData.health)
    println(tank.tankData.speed)





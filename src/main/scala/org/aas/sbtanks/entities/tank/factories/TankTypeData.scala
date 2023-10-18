package org.aas.sbtanks.entities.tank.factories

import org.aas.sbtanks.entities.*
import org.aas.sbtanks.entities.tank.*




sealed trait TankTypeData:
    def supplyData: TankData & TankDataUpdater



object TankTypeData:
    case object BasicTankData extends TankTypeData:
        override def supplyData = new TankData(1, 1) with TankDataUpdater

    case object FastTankData extends TankTypeData:
      override def supplyData = new TankData(1, 3) with TankDataUpdater


    case object PowerTankData extends TankTypeData:
      override def supplyData = new TankData(1, 2) with TankDataUpdater


    case object ArmorTankData extends TankTypeData:
      override def supplyData = new TankData(4, 2) with TankDataUpdater







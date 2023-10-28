package org.aas.sbtanks.player

import org.aas.sbtanks.entities.tank.factories.TankTypeData
import org.aas.sbtanks.entities.tank.TankData
import org.aas.sbtanks.entities.tank.TankDataUpdater

case object PlayerTankData extends TankTypeData:
    override def supplyData = new TankData(3, 1, 1) with TankDataUpdater

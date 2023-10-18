package org.aas.sbtanks.entities.tank.factories

import org.aas.sbtanks.entities.*
import org.aas.sbtanks.entities.tank.*




sealed trait TankTypeData:
  def supplyData: TankData & TankDataUpdater

  

case object BasicTank extends TankTypeData:
  override def supplyData = new TankData(1, 1) with TankDataUpdater


case object FastTank extends TankTypeData:
  override def supplyData = new TankData(1, 3) with TankDataUpdater


case object PowerTank extends TankTypeData:
  override def supplyData = new TankData(1, 2) with TankDataUpdater


case object ArmorTank extends TankTypeData:
  override def supplyData = new TankData(4, 2) with TankDataUpdater







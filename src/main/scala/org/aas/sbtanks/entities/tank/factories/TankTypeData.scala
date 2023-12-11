package org.aas.sbtanks.entities.tank.factories

import org.aas.sbtanks.entities.*
import org.aas.sbtanks.entities.tank.*



/**
 * A trait representing the data associated with a specific type of tank.
 */
trait TankTypeData:
    def apply() = supplyData

    /**
     * Supplies the tank data for the specific tank type.
     *
     * @return The tank data for the specific tank type.
     */
    def supplyData: TankData & TankDataUpdater

/**
 * The data for a basic tank with default characteristics.
 */
case object BasicTankData extends TankTypeData:
    override def supplyData = new TankData(1, 1, 1) with TankDataUpdater

/**
 * The data for a fast tank with default characteristics.
 */
case object FastTankData extends TankTypeData:
  override def supplyData = new TankData(1, 3, 2) with TankDataUpdater

/**
 * The data for a power tank with default characteristics.
 */
case object PowerTankData extends TankTypeData:
  override def supplyData = new TankData(1, 2, 3) with TankDataUpdater

/**
 * The data for a armor tank with default characteristics.
 */
case object ArmorTankData extends TankTypeData:
  override def supplyData = new TankData(4, 2, 2) with TankDataUpdater







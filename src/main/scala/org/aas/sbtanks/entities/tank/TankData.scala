package org.aas.sbtanks.entities.tank

trait TankData:
    def health: Int

    def speed: Int

trait ModifiableTankData extends TankData :

    protected def getInstance(health: Int, speed: Int): ModifiableTankData

    def updateHealth(f: Int => Int): ModifiableTankData = getInstance(f(health), speed)

    def updateSpeed(f: Int => Int): ModifiableTankData = getInstance(health, f(speed))

case class DynamicTankData(override val health: Int, override val speed: Int) extends ModifiableTankData :
    override def getInstance(health: Int, speed: Int): ModifiableTankData = DynamicTankData(health, speed)


case class FixedTankData(override val health: Int, override val speed: Int) extends TankData


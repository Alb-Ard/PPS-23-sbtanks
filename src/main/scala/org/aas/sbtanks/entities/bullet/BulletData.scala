package org.aas.sbtanks.entities.bullet

trait BulletData:
  def speed: Int

trait ModifiableBulletData extends BulletData :

  protected def getInstance(speed: Int): ModifiableTankData

  def updateSpeed(f: Int => Int): ModifiableBulletData = getInstance(f(speed))

case class DynamicBulletData(override val speed: Int) extends ModifiableBulletData :
  override def getInstance(speed: Int): ModifiableBulletData = DynamicBulletData(speed)


case class FixedBulletData(override val speed: Int) extends BulletData

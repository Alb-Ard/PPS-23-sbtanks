package org.aas.sbtanks.entities.bullet.factories

import org.aas.sbtanks.entities.bullet.*

trait BulletTypeData:
    def apply() = getBulletData()

    def getBulletData: BulletData & BulletSpeedUpdater


case object BasicBullet extends BulletTypeData:
    override def getBulletData = new BulletData(1)

case object DoubleSpeedBullet extends BulletTypeData:
    override def getBulletData = new BulletData(1) with BulletSpeedUpdater



package org.aas.sbtanks.entities.bullet.structure

import org.aas.sbtanks.entities.bullet.{BulletData, BulletSpeedUpdater}
import org.aas.sbtanks.entities.bullet.factories.*

trait Bullet(speedDataSupplier: BulletTypeData):
    def updateSpeed(newSpeed: BulletData & DoubleSpeedBullet): Unit = bulletData = newSpeed


    var bulletData: BulletData & DoubleSpeedBullet = speedDataSupplier()

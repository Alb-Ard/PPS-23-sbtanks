package org.aas.sbtanks.entities.bullet.structure

import org.aas.sbtanks.entities.bullet.{BulletData, BulletSpeedUpdater}
import org.aas.sbtanks.entities.bullet.factories.*

trait Bullet(speedDataSupplier: BulletTypeData):
    def updateSpeedData(newSpeed: BulletData & BulletSpeedUpdater): Unit = bulletData = newSpeed


    var bulletData: BulletData & BulletSpeedUpdater = speedDataSupplier()

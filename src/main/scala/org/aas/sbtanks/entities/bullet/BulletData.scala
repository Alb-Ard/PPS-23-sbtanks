package org.aas.sbtanks.entities.bullet

case class BulletData(speed: Int)

trait DoubleSpeedBullet extends BulletData :

    this: BulletData =>
    def doubleSpeed(): BulletData & DoubleSpeedBullet = new BulletData(speed * 2) with DoubleSpeedBullet


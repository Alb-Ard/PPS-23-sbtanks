package org.aas.sbtanks.entities.bullet

case class BulletData(speed: Int)

trait BulletSpeedUpdater extends BulletData :

    self: BulletData =>
    def doubleSpeed(): BulletData & BulletSpeedUpdater = new BulletData(speed * 2) with BulletSpeedUpdater

object BulletExample extends App:

    var normalBullet = BulletData(50)

    var doubleSpeedBullet = new BulletData(normalBullet.speed) with BulletSpeedUpdater

    doubleSpeedBullet.doubleSpeed()


    println(s"Normal Bullet Speed: $normalBullet")
    println(s"Doubled Bulled Speed: $doubleSpeedBullet")


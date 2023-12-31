package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.powerups.PowerUp.{FuncPowerUp, PowerUpConstraint}
import org.aas.sbtanks.entities.tank.structure.Tank



val h: Tank => Tank = t => {t updateTankData(t.tankData.updateHealth(_ + 10)); t}
val h2: Tank => Tank = t => {t updateTankData(t.tankData.updateHealth(_ - 10)); t}
object HealthUp extends FuncPowerUp[Tank](h, h2) with PowerUpConstraint[Tank](entity => entity.isInstanceOf[Tank])


val s: Tank => Tank = t => {t updateTankData(t.tankData.updateSpeed(_ + 10)); t}
val s2: Tank => Tank = t => {t updateTankData(t.tankData.updateSpeed(_ - 10)); t}

object SpeedUp extends FuncPowerUp[Tank](s, s2) with PowerUpConstraint[Tank](entity => entity.isInstanceOf[Tank])


val sb: Tank => Tank = t => {t updateTankData(t.tankData.updateBulletSpeed(_ + 10)); t}
val sb2: Tank => Tank = t => {t updateTankData(t.tankData.updateBulletSpeed(_ - 10)); t}

object SpeedBulletUp extends FuncPowerUp[Tank](sb, sb2) with PowerUpConstraint[Tank](entity => entity.isInstanceOf[Tank])
    








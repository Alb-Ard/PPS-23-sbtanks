package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.entities.powerups.PowerUp.{ContextualFuncPowerUp, PowerUpConstraint}
import org.aas.sbtanks.entities.powerups.TimeablePowerUp
import org.aas.sbtanks.entities.powerups.contexts.CachedContext
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.player.PlayerTank


object Timer:
    import TimerPowerUpUtils.*

    case class TimerPowerUp()
        extends ContextualFuncPowerUp[CachedContext[(Int, Int)], Tank](CachedContext[(Int, Int)]())(f, g)
        with TimeablePowerUp(STOP_TIME_POWER_UP_DURATION)
        with PowerUpConstraint[Tank](constraint)


object TimerPowerUpUtils:
    val STOP_TIME_POWER_UP_DURATION = 10.0
    private val FROZEN_BULLET_SPEED = 0
    private val FROZEN_TANK_SPEED = 0

    val f: (CachedContext[(Int, Int)], Tank) => Tank =
        (c,t) =>
            c.provide((t.tankData.speed, t.tankData.bulletSpeed))
            t updateTankData(t.tankData
                .updateSpeed(_ => FROZEN_TANK_SPEED)
                .updateBulletSpeed(_ => FROZEN_BULLET_SPEED));
            t

    val g: (CachedContext[(Int, Int)], Tank) => Tank =
        (c,t) =>
            val (speed, bulletSpeed) = c.getAndClear().get
            t updateTankData(t.tankData
                .updateSpeed(_ => speed)
                .updateBulletSpeed(_ => bulletSpeed));
            t

    val constraint: Tank => Boolean =
        !_.isInstanceOf[PlayerTank]
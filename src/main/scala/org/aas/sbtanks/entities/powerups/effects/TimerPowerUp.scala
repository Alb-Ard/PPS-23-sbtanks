package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.entities.powerups.PowerUp.ContextualFuncPowerUp
import org.aas.sbtanks.entities.powerups.TimeablePowerUp
import org.aas.sbtanks.entities.powerups.contexts.CachedContext
import org.aas.sbtanks.entities.tank.structure.Tank


object Timer:
    import TimerPowerUpUtils.*

    case object TimerPowerUp
        extends ContextualFuncPowerUp[CachedContext[(Int, Int)], Tank](CachedContext[(Int, Int)]())(f, g)
        with TimeablePowerUp(STOP_TIME_POWER_UP_DURATION)


object TimerPowerUpUtils:
    val STOP_TIME_POWER_UP_DURATION = 2000L
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
            val (speed, bulletSpeed) = c.provide((t.tankData.speed, t.tankData.bulletSpeed))
            t updateTankData(t.tankData
                .updateSpeed(_ => speed)
                .updateBulletSpeed(_ => bulletSpeed));
            t
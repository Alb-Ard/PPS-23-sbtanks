package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.entities.powerups.PowerUp.{ContextualFuncPowerUp, PowerUpConstraint}
import org.aas.sbtanks.entities.powerups.TimeablePowerUp
import org.aas.sbtanks.entities.powerups.contexts.{CachedContext, MapContext}
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.player.PlayerTank


object Timer:
    import TimerPowerUpUtils.*

    case class TimerPowerUp()
        extends ContextualFuncPowerUp[MapContext[Tank, (Int, Int)], Tank](MapContext[Tank, (Int, Int)]())(f, g)
        with TimeablePowerUp(STOP_TIME_POWER_UP_DURATION)
        with PowerUpConstraint[Tank](constraint)


object TimerPowerUpUtils:
    val STOP_TIME_POWER_UP_DURATION = 10D
    private val FROZEN_BULLET_SPEED = 0
    private val FROZEN_TANK_SPEED = 0

    val f: (MapContext[Tank, (Int, Int)], Tank) => Tank =
        (c,t) =>
            c.registerEntity(t, (t.tankData.speed, t.tankData.bulletSpeed))
            t updateTankData(t.tankData
                .updateSpeed(_ => FROZEN_TANK_SPEED)
                .updateBulletSpeed(_ => FROZEN_BULLET_SPEED))
            t

    val g: (MapContext[Tank, (Int, Int)], Tank) => Tank =
        (c,t) =>
            println(c)
            val (speed, bulletSpeed) = c.getValue(t).get
            t updateTankData(t.tankData
                .updateSpeed(_ => speed)
                .updateBulletSpeed(_ => bulletSpeed));
            t

    val constraint: Tank => Boolean =
        !_.isInstanceOf[PlayerTank]
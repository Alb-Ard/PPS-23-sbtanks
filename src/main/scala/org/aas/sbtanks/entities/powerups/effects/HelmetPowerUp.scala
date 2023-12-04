package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.entities.powerups.PowerUp.ContextualFuncPowerUp
import org.aas.sbtanks.entities.powerups.TimeablePowerUp
import org.aas.sbtanks.entities.powerups.contexts.CachedContext
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank


object Helmet:
    import HelmetPowerUpUtils.*

    case object HelmetPowerUp extends ContextualFuncPowerUp[CachedContext[Int], Tank](CachedContext[Int]())(f, g) with TimeablePowerUp(HELMET_POWER_UP_DURATION)

object HelmetPowerUpUtils:
    val HELMET_POWER_UP_DURATION = 2000L
    private val INVINCIBILITY: Int = Int.MaxValue


    val f: (CachedContext[Int], Tank) => Tank =
        (c, t) =>
            c.provide(t.tankData.health)
            t updateTankData(t.tankData.updateHealth(_ => INVINCIBILITY))
            t



    val g: (CachedContext[Int], Tank) => Tank =
        (c, t) =>
            t updateTankData(t.tankData.updateHealth(_ => c.getAndClear().get))
            t


object test extends App:
    import Helmet.*

    var tank = BasicTank()

    val p = HelmetPowerUp

    println(tank.tankData.health)

    tank = p(tank)

    println(tank.tankData.health)

    tank = p.revert(tank)

    println(tank.tankData.health)





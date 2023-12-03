package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.DirectionBehaviour
import org.aas.sbtanks.entities.powerups.PowerUp.ContextualFuncPowerUp
import org.aas.sbtanks.entities.powerups.contexts.{CachedContext, CounterContext}
import org.aas.sbtanks.entities.powerups.{SpeedBulletUp, SpeedUp}
import org.aas.sbtanks.entities.tank.behaviours.TankMultipleShootingBehaviour
import org.aas.sbtanks.entities.tank.factories.PowerTankData
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.{BasicTank, PowerTank}


object Star:
    import StarPowerUpUtils.*
    case class StarPowerUp() extends ContextualFuncPowerUp[(CounterContext, CachedContext[Int]), Tank]((CounterContext(0), CachedContext[Int]()))(f, g)


object StarPowerUpUtils:

    private val DEFAULT_NUMBER_BULLETS = 1
    private val INCREASED_NUMBER_BULLETS = 2




    val f: ((CounterContext,  CachedContext[Int]), Tank) => Tank =
        case ((counter@CounterContext(0), cached: CachedContext[Int]), t: Tank) =>
            cached.provide(t.tankData.bulletSpeed)
            t.updateTankData(t.tankData.updateBulletSpeed(_ => PowerTankData.supplyData.bulletSpeed))
            counter += 1
            t
        case ((counter@CounterContext(1), cached: CachedContext[Int]), t: Tank) =>
            t.asInstanceOf[TankMultipleShootingBehaviour].shots = INCREASED_NUMBER_BULLETS
            counter += 1
            t

    val g: ((CounterContext, CachedContext[Int]), Tank) => Tank =
        case ((counter@CounterContext(1), cached: CachedContext[Int]), t: Tank) =>
            t.updateTankData(t.tankData.updateBulletSpeed(_ => cached.getAndClear().get))
            t
        case ((counter@CounterContext(2), cached: CachedContext[Int]) , t: Tank) =>
            t.asInstanceOf[TankMultipleShootingBehaviour].shots = DEFAULT_NUMBER_BULLETS
            counter -= 1
            t



object a extends App:
    import Star.*
    import StarPowerUpUtils.*



    val sp = new StarPowerUp

    var tank = new BasicTank with TankMultipleShootingBehaviour with PositionBehaviour with DirectionBehaviour



    println(tank.tankData.bulletSpeed)
    println(tank.shots)

    tank = sp(tank)

    println(tank.tankData.bulletSpeed)

    tank = sp(tank)

    println(tank.shots)

    tank = sp.revert(tank)

    println(tank.shots)

    tank = sp.revert(tank)

    println(tank.tankData.bulletSpeed)
    println(tank.shots)



    /*

    var num = 10


    num = sp(num)

    println(num)

    println(tank.tankData)

    tank = sp(tank)

    println(tank.tankData)

    num = sp(num)

    println(num)

    tank = sp(tank)

    println(tank.tankData)

    num = sp(num)

    println(num)
     */












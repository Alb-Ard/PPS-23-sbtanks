package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.entities.powerups.PowerUp.ContextualFuncPowerUp
import org.aas.sbtanks.entities.powerups.contexts.CounterContext
import org.aas.sbtanks.entities.powerups.{ContextualChainPowerUp, SpeedBulletUp, SpeedUp}
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank


object Star:
    import StarPowerUpUtils.*
    case object StarPowerUp extends ContextualFuncPowerUp[CounterContext, Tank | Int](CounterContext(0))(f, g)


object StarPowerUpUtils:

    /* TODO
        1- replace Int with Obstacles
        2- (blocking) second powerup needs double bullet
        3- (blocking) adds constraint on the obstacle
     */
    val f: (CounterContext, Tank | Int) => Tank | Int = (c, t) => (c, t) match
        case (CounterContext(0), tt: Tank) => SpeedBulletUp(tt); c += 1; tt
        case (CounterContext(1), tt: Tank) => SpeedUp(tt); c += 1; tt
        case (CounterContext(2), i: Int) => c += 1; i * 2
        case _ => t

    val g: (CounterContext, Tank | Int) => Tank | Int = (c, t) => (c, t) match
        case (CounterContext(0), tt: Tank) => SpeedBulletUp.revert(tt); c -= 1; tt
        case (CounterContext(1), tt: Tank) => SpeedUp.revert(tt); c -= 1; tt
        case (CounterContext(2), i: Int) => i / 2
        case _ => t



object a extends App:
    import Star.*

    val sp = StarPowerUp

    var tank = BasicTank()

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












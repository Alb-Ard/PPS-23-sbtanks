package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.behaviours.{DamageableBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.powerups.PowerUp.{ContextualFuncPowerUp, FuncPowerUp, PowerUpConstraint}
import org.aas.sbtanks.entities.powerups.TimeablePowerUp
import org.aas.sbtanks.entities.powerups.contexts.CachedContext
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.aas.sbtanks.player.PlayerTank


object Helmet:
    import HelmetPowerUpUtils.*

    case class HelmetPowerUp() extends FuncPowerUp[Tank](f, g) with TimeablePowerUp(HELMET_POWER_UP_DURATION) with PowerUpConstraint[Tank](constraint)

object HelmetPowerUpUtils:
    val HELMET_POWER_UP_DURATION = 15.0


    val f: Tank => Tank =
        t => t.asInstanceOf[DamageableBehaviour].setDamageable(false).asInstanceOf[Tank]



    val g: Tank => Tank =
        t => t.asInstanceOf[DamageableBehaviour].setDamageable(true).asInstanceOf[Tank]
    
    
    val constraint: Tank => Boolean =
        _.isInstanceOf[PlayerTank]


object test extends App:
    import Helmet.*

    var tank = new BasicTank with DamageableBehaviour:
        override protected def applyDamage(amount: Int): this.type =
            updateTankData(tankData.updateHealth(_ - 1))
            tankData.health match
                case v if v <= 0 =>
                    destroyed(())
                    this
                case _ => this

    val p = new HelmetPowerUp with PositionBehaviour


    tank = p(tank)

    tank = tank.damage(1)
    println(tank.tankData.health)


    tank = p.revert(tank)

    tank = tank.damage(1)
    println(tank.tankData.health)






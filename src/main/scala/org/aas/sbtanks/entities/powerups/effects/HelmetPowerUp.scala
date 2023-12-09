package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.behaviours.{DamageableBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.powerups.PowerUp.{ContextualFuncPowerUp, FuncPowerUp, PowerUpConstraint}
import org.aas.sbtanks.entities.powerups.TimeablePowerUp
import org.aas.sbtanks.entities.powerups.contexts.CachedContext
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.aas.sbtanks.player.PlayerTank

/**
 * Object representing the Helmet power-up, providing utility functions and a case class for the power-up instance.
 */
object Helmet:
    import HelmetPowerUpUtils.*


    /**
     * Case class representing the Helmet power-up, which is a functional power-up for tanks with a specific constraint on its use a time effect.
     *
     *
     */
    case class HelmetPowerUp() extends FuncPowerUp[Tank](f, g) with TimeablePowerUp(HELMET_POWER_UP_DURATION) with PowerUpConstraint[Tank](constraint)

object HelmetPowerUpUtils:
    val HELMET_POWER_UP_DURATION = 15.0


    val f: Tank => Tank =
        t => t.asInstanceOf[DamageableBehaviour].setDamageable(false).asInstanceOf[Tank]



    val g: Tank => Tank =
        t => t.asInstanceOf[DamageableBehaviour].setDamageable(true).asInstanceOf[Tank]
    
    
    val constraint: Tank => Boolean =
        _.isInstanceOf[PlayerTank]







package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.entities.powerups.PowerUp.{PowerUp, PowerUpConstraint}
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.entities.powerups.effects.Grenade.GrenadePowerUp
import org.aas.sbtanks.player.PlayerTank

import scala.util.Random

/**
 * The Grenade object represents a power-up in the game.
 */
object Grenade:
    import org.aas.sbtanks.entities.powerups.PowerUp.FuncPowerUp
    import GrenadePowerUpUtils.*

    /**
     * A case object representing the Grenade power-up, which is a functional power-up for tanks with a specific constraint on its use.
     * It combines functions 'f' and 'g' with a specified constraint.
     */
    case class GrenadePowerUp() extends FuncPowerUp[Tank](f, g) with PowerUpConstraint[Tank](constraint)

/**
 * Utility functions related to the Grenade power-up.
 */
object GrenadePowerUpUtils:

    /**
     * Function 'f' represents the effect of the Grenade power-up on a tank.
     * In this case, it updates the tank's health to 0.
     */
    val f: Tank => Tank = t => {t updateTankData(t.tankData.updateHealth(_ => 0)); t}

    /**
     * Function 'g' represents the identity function, indicating no additional modification to the tank.
     */
    val g: Tank => Tank = identity

    /**
     * The constraint for the Grenade power-up, specifying when it can be applied to a tank.
     * In this case, the power-up cannot be applied to instances of PlayerTank.
     */
    val constraint: Tank => Boolean =
        !_.isInstanceOf[PlayerTank]





object x extends App:
    import org.aas.sbtanks.entities.powerups.effects.Grenade.*




    val t: Tank with PositionBehaviour = new BasicTank with PositionBehaviour

    val t2: Tank with PositionBehaviour = new PlayerTank with PositionBehaviour

    val p = new GrenadePowerUp with PositionBehaviour


    def applyP(p: PowerUp[Tank], t: Tank) =
        println(p.asInstanceOf[PositionBehaviour].positionX)
        p(t)

    println(p(t).tankData.health)
    println(p(t2).tankData.health)









package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.entities.powerups.PowerUp.{PowerUp, PowerUpConstraint}
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.aas.sbtanks.behaviours.{DamageableBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.powerups.TimeablePowerUp
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
     * Being an instantaneous powerup its duration effect is 0
     * It combines functions 'f' and 'g' with a specified constraint.
     */
    case class GrenadePowerUp() extends FuncPowerUp[Tank](f, g) with PowerUpConstraint[Tank](constraint) with TimeablePowerUp(0)

/**
 * Utility functions related to the Grenade power-up.
 */
object GrenadePowerUpUtils:

    /**
     * Function 'f' represents the effect of the Grenade power-up on a tank.
     * In this case, it make sure the tank is destroyed.
     */
    val f: Tank => Tank = t => {t.asInstanceOf[DamageableBehaviour].destroyed(()); t}

    /**
     * Function 'g' represents the identity function, indicating no additional modification to the tank.
     * <p>
     * Note: this reversion function is technically never called
     * </p>
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




    val t = new BasicTank with PositionBehaviour with DamageableBehaviour:
        override protected def applyDamage(amount: Int): this.type =
            updateTankData(tankData.updateHealth(_ - 1))
            tankData.health match
                case v if v <= 0 =>
                    destroyed(())
                    this
                case _ => this

    val t2 = new PlayerTank with PositionBehaviour with DamageableBehaviour:
        override protected def applyDamage(amount: Int): this.type =
            updateTankData(tankData.updateHealth(_ - 1))
            tankData.health match
                case v if v <= 0 =>
                    destroyed(())
                    this
                case _ => this

    val p = new GrenadePowerUp with PositionBehaviour

    t.destroyed += { - =>
        println("t destroyed")
    }

    t2.destroyed += { - =>
        println("t2 destroyed")
    }

    def applyP(p: PowerUp[Tank], t: Tank) =
        println(p.asInstanceOf[PositionBehaviour].positionX)
        p(t)


    t.asInstanceOf

    println(p(t).tankData.health)
    println(p(t2).tankData.health)









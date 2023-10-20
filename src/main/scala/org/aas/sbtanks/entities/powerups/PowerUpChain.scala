package org.aas.sbtanks.entities.powerups
import org.aas.sbtanks.entities.powerups.PowerUp.*

import scala.annotation.tailrec
import org.aas.sbtanks.entities.tank.structure.Tank

class PowerUpChain[E](powerUps: PowerUp[E]*) extends PowerUp[E]:
    override def apply(entity: E): E =
        powerUps.foldLeft(entity)((acc, powerUp) => powerUp.apply(acc))

    def andThen(next: PowerUp[E]): PowerUpChain[E] = PowerUpChain(powerUps :+ next: _*)

    

object PowerUpChain extends App:
    val p = new PowerUpChain[Tank](FuncPowerUp(t => t))
        .andThen(t => t)
        .andThen(new FuncPowerUp((t: Tank) => t) with PowerUpConstraint((t: Tank) => t != null))








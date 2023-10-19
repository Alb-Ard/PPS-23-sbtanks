package org.aas.sbtanks.entities.powerups
import org.aas.sbtanks.entities.powerups.PowerUp.*

import scala.annotation.tailrec

trait PowerUpChain[E](powerUps: PowerUp[E]*) extends PowerUp[E]:
    override def apply(entity: E): E =
        powerUps.foldLeft(entity)((acc, powerUp) => powerUp.apply(acc))

    def andThen(next: PowerUp[E]): PowerUpChain[E] = ??? //new PowerUpChain(powerUps :+ next: _*)






object PowerUpChain extends App:












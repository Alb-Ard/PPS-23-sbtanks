package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank




object PowerUp:

    trait PowerUp[E]:
        def apply(entity: E): E


    trait PowerUpConstraint[E](predicate: E => Boolean) extends PowerUp[E]:
        abstract override def apply(entity: E): E = Some(entity).filter(predicate).map(super.apply).getOrElse(entity)


    case class FuncPowerUp[E](f: E => E) extends PowerUp[E]:
        override def apply(entity: E): E = f(entity)


    object PowerUp extends App:
        def applytoAll[E](powerUp: PowerUp[E])(entities: E*): Seq[E] = entities.map(powerUp.apply)













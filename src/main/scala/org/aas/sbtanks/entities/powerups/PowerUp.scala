package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank




object PowerUp:

    trait PowerUp[E]:
        def apply(entity: E): E
        def revert(entity: E): E




    trait PowerUpConstraint[E](predicate: E => Boolean, reversePredicate: E => Boolean) extends PowerUp[E]:
        abstract override def apply(entity: E): E = Some(entity).filter(predicate).map(super.apply).getOrElse(entity)

        abstract override def revert(entity: E): E =  Some(entity).filter(reversePredicate).map(super.revert).getOrElse(entity)



    /*
    trait CombinablePowerUp[E] extends PowerUp[E]:
        def combine(other: CombinablePowerUp[E]): CombinablePowerUp[E] = CombinablePowerUp.combine(this, other)
    */

    //trait PowerUpTank[E <: Tank] extends PowerUpConstraint[E](entity => entity.isInstanceOf[Tank]):


    case class FuncPowerUp[E](f: E => E, g: E => E) extends PowerUp[E]:
        override def apply(entity: E): E = f(entity)
        override def revert(entity: E): E = g(entity)



    object PowerUp extends App:
        def applytoAll[E](powerUp: PowerUp[E])(entities: E*): Seq[E] = entities.map(powerUp.apply)













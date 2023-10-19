package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank




object PowerUp:

    trait PowerUp[E](f: E => E):
        def apply(entity: E): E = f(entity)



    trait PowerUpConstraint[E](predicate: E => Boolean) extends PowerUp[E]:
        override def apply(entity: E): E = Some(entity).filter(predicate).map(super.apply).getOrElse(entity)




    object PowerUp extends App:
        def applytoAll[E](powerUp: PowerUp[E])(entities: E*): Seq[E] = entities.map(powerUp.apply)













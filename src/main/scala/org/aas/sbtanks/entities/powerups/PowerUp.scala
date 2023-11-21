package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank




object PowerUp:

    abstract class PowerUp[E]:
        def apply[A <: E](entity: A): A
        def revert[A <: E](entity: A): A

        def combineWith(others: Seq[PowerUp[E]]): Seq[PowerUp[E]] = others :+ this



    trait PowerUpConstraint[E](predicate: E => Boolean) extends PowerUp[E]:
        this: PowerUp[E] =>
        abstract override def apply[A <: E](entity: A): A = Some(entity).filter(predicate).map(super.apply).getOrElse(entity)

    /**
      * @param f Function that should apply the effect by returning a modified entity of the same type
      * @param g Function that should undo the effect by returning a modified entity of the same type
      */
    class FuncPowerUp[E](val f: E => E, val g: E => E) extends PowerUp[E]:
        override def apply[A <: E](entity: A): A = f(entity).asInstanceOf[A]
        override def revert[A <: E](entity: A): A = g(entity).asInstanceOf[A]

    class ContextualFuncPowerUp[C, E](context: C)(f: (C, E) => E, g: (C, E) => E)
        extends FuncPowerUp[E](e => f(context, e), e => g(context, e))


    object PowerUp extends App:
        def applytoAll[E](powerUp: PowerUp[E])(entities: E*): Seq[E] = entities.map(powerUp.apply)













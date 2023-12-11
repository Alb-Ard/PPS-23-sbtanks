package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank




object PowerUp:

    /**
     * Abstract class representing a power-up that can be applied to entities of type E.
     *
     * @tparam E The type of entities that can be affected by the power-up.
     */
    abstract class PowerUp[E]:
        /**
         * Applies the power-up to the specified entity.
         *
         * @param entity The entity to which the power-up is applied.
         * @tparam A The specific subtype of E to which the power-up is applied.
         * @return The modified entity after applying the power-up.
         */
        def apply[A <: E](entity: A): A

        /**
         * Reverts the effect of the power-up on the specified entity.
         *
         * @param entity The entity from which the power-up effect is reverted.
         * @tparam A The specific subtype of E from which the power-up effect is reverted.
         * @return The modified entity after reverting the power-up effect.
         */
        def revert[A <: E](entity: A): A

        /**
         * Combines this power-up with others and returns a new sequence of power-ups.
         *
         * @param others The sequence of other power-ups to combine with this power-up.
         * @return A new sequence containing this power-up and the provided power-ups.
         */
        def combineWith(others: Seq[PowerUp[E]]): Seq[PowerUp[E]] = others :+ this


    /**
     * A trait representing a power-up constraint that can be applied to entities of type E based on a predicate.
     * Note: it rapresents a middleware for PowerUp abstract class
     *
     * @tparam E The type of entities that can be affected by the power-up constraint.
     * @param predicate The predicate used to constrain the application of the power-up.
     */
    trait PowerUpConstraint[E](predicate: E => Boolean) extends PowerUp[E]:
        this: PowerUp[E] =>

        /**
         * Applies the power-up constraint based on the parent apply method only if the provided predicate is true.
         *
         * @param entity The entity to which the power-up constraint is applied.
         * @tparam A The specific subtype of E to which the power-up constraint is applied.
         * @return The modified entity after applying the power-up constraint.
         */
        abstract override def apply[A <: E](entity: A): A = Some(entity).filter(predicate).map(super.apply).getOrElse(entity)
        abstract override def revert[A <: E](entity: A): A = Some(entity).filter(predicate).map(super.revert).getOrElse(entity)

    /**
      * @param f Function that should apply the effect by returning a modified entity of the same type
      * @param g Function that should undo the effect by returning a modified entity of the same type
      */
    class FuncPowerUp[E](val f: E => E, val g: E => E) extends PowerUp[E]:
        override def apply[A <: E](entity: A): A = f(entity).asInstanceOf[A]
        override def revert[A <: E](entity: A): A = g(entity).asInstanceOf[A]

    /**
     * A class representing a contextual functional power-up that operates on entities of type E with a contextual parameter of type C.
     *
     * @tparam C The type of the contextual parameter.
     * @tparam E The type of entities that can be affected by the power-up.
     * @param context The contextual parameter used to provide additional support on the functional computation.
     * @param f       The function to apply.
     * @param g       The function to revert.
     */
    class ContextualFuncPowerUp[C, E](context: C)(f: (C, E) => E, g: (C, E) => E)
        extends FuncPowerUp[E](e => f(context, e), e => g(context, e))


    object PowerUp extends App:
        def applytoAll[E](powerUp: PowerUp[E])(entities: E*): Seq[E] = entities.map(powerUp.apply)













package org.aas.sbtanks.enemies.ai.fsm

import org.aas.sbtanks.enemies.ai.State.State

/**
 * A trait representing an abstract state machine using the State monadic construct. It extends StateMachine to provide
 * implementations for state transition operations and inherits from AbstractStateModifier for state manipulation.
 *
 * It extends StateMachine binding the internal E type to adapt the State 2-kinded type to a 1-kinded one
 *
 * @tparam E The type of elements in the state.
 * @tparam V The type of values used for transitions.
 */
trait AbstractStateMachine[E, V] extends StateMachine[({type F[A] = State[E,A]})#F, E, V] with AbstractStateModifier[E]:

    /**
     * Creates a new State monadic construct supplied with a pure E value.
     *
     * @param a The value to wrap in the State monadic construct.
     * @tparam A The type of the value.
     * @return The State monad wrapping the value.
     */
    protected def pure[A](a: A): State[E,A] = State(s => (a, s))




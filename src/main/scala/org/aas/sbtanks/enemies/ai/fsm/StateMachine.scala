package org.aas.sbtanks.enemies.ai.fsm

import org.aas.sbtanks.enemies.ai.State.Monad

/**
 * Trait representing a state machine that operates on a state of type `S` with elements of type `E` and transitions using values of type `A`.
 * It requires a mixin of the `StateModifier` trait to handle state modifications.
 *
 * @tparam S The type of the state container.
 * @tparam E The type of elements in the state.
 * @tparam A The type of values used for transitions.
 */
trait StateMachine[S[_], E, A]:
    this: StateModifier[S, E] =>

    /**
     * Performs a state transition based on the provided value.
     *
     * @param value The value used for the state transition.
     * @return The modified state after the transition of type `S[Unit]` (only the state is changed).
     */
    def transition(value: A): S[Unit]




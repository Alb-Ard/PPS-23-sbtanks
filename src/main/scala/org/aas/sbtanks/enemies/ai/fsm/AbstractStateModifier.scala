package org.aas.sbtanks.enemies.ai.fsm

import org.aas.sbtanks.enemies.ai.State.State
import org.aas.sbtanks.enemies.ai.fsm.movement.AiMovementStateMachine.getState

/**
 * A trait representing an abstract state modifier using the State monadic constructs family. It extends StateModifier to provide
 * implementations for state manipulation operations.
 *
 * It extends StateModifier binding the internal E type to adapt the State 2-kinded type to a 1-kinded one
 *
 * @tparam E The type of elements in the state.
 */
trait AbstractStateModifier[E] extends StateModifier[({type F[A] = State[E,A]})#F, E]:

    /**
     * Retrieves the current state as return value using the State monadic construct.
     *
     * @return The current state wrapped in the State monadic construct.
     */
    override def getState: State[E,E] = State(s => (s, s))

    /**
     * Sets the state to a new value using the State monadic construct.
     *
     * @param s The new state value.
     * @return The modified state wrapped in the State monadic construct without a return a value.
     */
    override def setState(s: E): State[E, Unit] = State(_ => ((), s))

    /**
     * Retrieves a value derived from the current state using the provided function in the State monadic construct.
     *
     * @param f The function to apply to the current state.
     * @tparam A The type of the value to retrieve.
     * @return The value derived from the state wrapped in the State monadic construct.
     */
    override def gets[A](f: E => A): State[E, A] = State(s => (f(s), s))

    /**
     * Modifies the current state using the provided function in the State monadic construct.
     *
     * @param f The function to apply to the current state.
     * @return The modified state wrapped in the State monadic construct without a return value.
     */
    override def modify(f: E => E): State[E, Unit] = 
        for
            s <- getState
            _ <- setState(f(s))
        yield ()
        






    






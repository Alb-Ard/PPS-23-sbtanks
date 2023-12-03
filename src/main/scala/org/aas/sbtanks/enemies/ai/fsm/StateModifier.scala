package org.aas.sbtanks.enemies.ai.fsm

/**
 * Trait representing a state modifier, allowing manipulation of a state of type `S` with elements of type `E`.
 *
 * @tparam S The type of the state container.
 * @tparam E The type of elements in the state.
 */
trait StateModifier[S[_], E]:

    /**
     * Retrieves the current state.
     *
     * @return The current state of type S[E].
     */
    def getState: S[E]

    /**
     * Sets the state to a new value.
     *
     * @param s The new state value.
     * @return The modified state with no new internal result of type S[Unit].
     */
    def setState(s: E): S[Unit]

    /**
     * Retrieves a value derived from the current state using the provided function.
     *
     * @param f The function to apply to the current state.
     * @tparam A The type of the value to retrieve from the type E.
     * @return The value derived from the state of type S[A].
     */
    def gets[A](f: E => A): S[A]

    /**
     * Modifies the current state using the provided function.
     *
     * @param f The function to apply to the current state on the internal type E.
     * @return The modified state of type S[Unit].
     */
    def modify(f: E => E): S[Unit]

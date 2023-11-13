package org.aas.sbtanks.enemies.ai.fsm

import org.aas.sbtanks.enemies.ai.State.State

trait AbstractStateMachine[E, V] extends StateMachine[({type F[A] = State[E,A]})#F, E, V] with AbstractStateModifier[E]:
    private def pure[A](a: A): State[E,A] = State(s => (a, s))




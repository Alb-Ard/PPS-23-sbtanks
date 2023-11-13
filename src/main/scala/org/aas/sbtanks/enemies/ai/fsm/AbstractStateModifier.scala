package org.aas.sbtanks.enemies.ai.fsm

import org.aas.sbtanks.enemies.ai.State.State
import org.aas.sbtanks.enemies.ai.fsm.movement.AiMovementStateMachine.getState

trait AbstractStateModifier[E] extends StateModifier[({type F[A] = State[E,A]})#F, E]:
    override def getState: State[E,E] = State(s => (s, s))

    override def setState(s: E): State[E, Unit] = State(_ => ((), s))

    override def gets[A](f: E => A): State[E, A] = State(s => (f(s), s))

    override def modify(f: E => E): State[E, Unit] = 
        for
            s <- getState
            _ <- setState(f(s))
        yield ()
        






    






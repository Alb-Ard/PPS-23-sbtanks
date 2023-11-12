package org.aas.sbtanks.enemies.ai.fsm

import org.aas.sbtanks.enemies.ai.{AiMovement, AiMovementState}
import org.aas.sbtanks.enemies.ai.State.Monad

trait StateMachine[S[_], E, A]:
    this: StateModifier[S, E] =>

    def transition(value: A): S[Unit]




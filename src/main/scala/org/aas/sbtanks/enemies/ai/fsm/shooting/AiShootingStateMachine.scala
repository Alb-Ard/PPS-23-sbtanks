package org.aas.sbtanks.enemies.ai.fsm.shooting

import org.aas.sbtanks.enemies.ai.State.State
import org.aas.sbtanks.enemies.ai.fsm.{StateMachine, StateModifier}
import org.aas.sbtanks.enemies.ai.shooting.{AiShootingState, ShootingEntity}

class AiShootingStateMachine extends StateMachine[AiShootingState, ShootingEntity, Int] with StateModifier[AiShootingState, ShootingEntity]:

    private def pure[A](a: A): AiShootingState[A] = State(s => (a, s))
    override def transition(value: Int): AiShootingState[Unit] = ???

    override def setState(s: ShootingEntity): AiShootingState[Unit] = ???

    override def getState: AiShootingState[ShootingEntity] = ???

    override def modify(f: ShootingEntity => ShootingEntity): AiShootingState[Unit] = ???

    override def gets[A](f: ShootingEntity => A): AiShootingState[A] = ???


package org.aas.sbtanks.enemies.ai.fsm.shooting

import org.aas.sbtanks.enemies.ai.State.State
import org.aas.sbtanks.enemies.ai.fsm.{AbstractStateMachine, StateMachine, StateModifier}
import org.aas.sbtanks.enemies.ai.shooting.{AiShootingState, ShootingEntity}

class AiShootingStateMachine extends AbstractStateMachine[ShootingEntity, Any]:
    override def transition(value: Any): State[ShootingEntity, Unit] = ???




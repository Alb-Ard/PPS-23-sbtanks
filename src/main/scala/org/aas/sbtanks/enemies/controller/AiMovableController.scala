package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.MovementEntity
import org.aas.sbtanks.enemies.ai.fsm.movement.AiMovementStateMachineUtils.computeAiState


trait AiMovableController(val entity: MovementEntity, val offset: Double):

    def computeNewMovementState() =
        val (d, e) = computeAiState(entity, movementBias = offset)
        e.moveRelative(d(0) / offset, d(1) / offset)
        this







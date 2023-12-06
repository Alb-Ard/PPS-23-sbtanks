package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.MovementEntity
import org.aas.sbtanks.enemies.ai.fsm.movement.AiMovementStateMachineUtils.computeAiState
import org.aas.sbtanks.enemies.ai.fsm.shooting.AiShootingStateMachineUtils.checkAiPriorityTarget
import org.aas.sbtanks.enemies.ai.shooting.ShootingEntity


trait AiMovableController(val entity: MovementEntity with ShootingEntity, val offset: Double):
    def computeNewMovementState() =

        val (isTargetInSight, en) = checkAiPriorityTarget(entity)
        if !isTargetInSight then
            val (d, e) = computeAiState(en.asInstanceOf[MovementEntity], movementBias = offset)
            e.moveRelative(d(0) / offset, d(1) / offset)
        this







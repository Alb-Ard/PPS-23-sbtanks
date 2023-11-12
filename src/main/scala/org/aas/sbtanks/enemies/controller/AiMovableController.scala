package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.AiEntity
import org.aas.sbtanks.enemies.ai.fsm.AiMovementStateMachineUtils.computeAiState


trait AiMovableController(val entities: Seq[AiEntity]):

    def computeStates() =
        this.entities.map(computeAiState)
            .map((p, e) => e.setPosition(p._1, p._2))






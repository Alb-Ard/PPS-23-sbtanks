package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.*
import org.aas.sbtanks.enemies.ai.State.EnemyStateMonad.computeState
import org.aas.sbtanks.enemies.ai.EnemyUtils.Enemy
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.enemies.controller.AiMovableController

class EnemyController(private val enemyTanks: Seq[AiEntity]) extends AiMovableController(enemyTanks) with Steppable:

    override def step(delta: Double) =
        this.computeStates()
        this





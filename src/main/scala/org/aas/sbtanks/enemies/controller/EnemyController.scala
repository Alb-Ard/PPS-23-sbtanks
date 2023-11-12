package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.*
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.enemies.controller.AiMovableController

class EnemyController(private val enemyTanks: Seq[ControllableTank]) extends AiMovableController(enemyTanks.map(_.asInstanceOf[AiEntity])) with Steppable:

    override def step(delta: Double) =
        this.computeStates()
        this





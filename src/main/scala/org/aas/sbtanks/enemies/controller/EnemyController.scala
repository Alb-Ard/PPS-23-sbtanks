package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.*
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.enemies.controller.AiMovableController
import org.aas.sbtanks.entities.tank.controller.TankController
import org.aas.sbtanks.entities.tank.view.TankView

class EnemyController(private val enemyTanks: Seq[ControllableTank], private val enemyViews: Seq[TankView], viewScale: Double)
    extends TankController(enemyTanks.zip(enemyViews), viewScale)
    with  AiMovableController(enemyTanks.map(_.asInstanceOf[MovementEntity])) with Steppable:

    override def step(delta: Double) =
        this.computeStates()
        this





package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.*
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.enemies.controller.AiMovableController
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.tank.controller.TankController
import org.aas.sbtanks.entities.tank.view.TankView
import scalafx.scene.Node
import scalafx.stage.Stage
import scalafx.scene.layout.Pane

class EnemyController(private val enemyTank: ControllableTank, private val enemyView: TankView, viewScale: Double, tileSize: Double)
    extends TankController(Seq((enemyTank, enemyView)), viewScale, tileSize)
    with AiMovableController(Seq(enemyTank.asInstanceOf[MovementEntity]))
    with Steppable:

    override def step(delta: Double) =
        this.computeStates()
        this

object EnemyController:
    def factory(viewScale: Double, tileSize: Double)(context: EntityRepositoryContext[?, ?, ?], tank: ControllableTank, view: TankView) =
        new EnemyController(tank, view, viewScale, tileSize)

        






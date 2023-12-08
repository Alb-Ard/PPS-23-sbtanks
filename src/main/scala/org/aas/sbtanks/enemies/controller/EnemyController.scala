package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.*
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.enemies.controller.AiMovableController
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.tank.controller.TankController
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.enemies.ai.shooting.ShootingEntity
import scalafx.scene.Node
import scalafx.stage.Stage
import scalafx.scene.layout.Pane

class EnemyController[VSK, VS](using context: EntityRepositoryContext[Stage, VSK, VS])(private val enemyTank: ControllableTank, private val enemyView: TankView, viewScale: Double, tileSize: Double)
    extends TankController(Seq((enemyTank, enemyView)), viewScale)
    with AiMovableController(enemyTank.asInstanceOf[Tank with MovementEntity with ShootingEntity], tileSize, () => enemyTank.tankData.speed)
    with Steppable:

    private var test: Double = 3.0
    enemyTank.setPosition(enemyTank.positionX, enemyTank.positionY)

    override def step(delta: Double) =
        test -= delta
        if test <= 0.0 then
            this.enemyTank.destroyed(())
            test = 50
        this.computeNewMovementState()
        this

object EnemyController:
    def factory(viewScale: Double, tileSize: Double)(oldController: Steppable, context: EntityRepositoryContext[Stage, ?, ?], tank: ControllableTank, view: TankView) =
        new EnemyController(using context)(tank, view, viewScale, tileSize)

        






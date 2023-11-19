package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.obstacles.view.ObstacleView
import scalafx.scene.layout.Pane
import scalafx.stage.Stage

class EnemySpawnController(using context: EntityRepositoryContext[Stage, Pane])(private val enemyTank: ControllableTank, private val enemyView: ObstacleView, viewScale: Double) extends Steppable:
    private var timeToSpawn: Double = 3.0


    override def step(delta: Double): this.type =
        timeToSpawn -= delta
        //if timeToSpawn <= 0.0 then
        //TODO
        this

object EnemySpawnController:
    def factory(viewScale: Double)(context: EntityRepositoryContext[Stage, Pane], tank: ControllableTank, view: ObstacleView) =
        new EnemySpawnController(using context)(tank, view, viewScale)
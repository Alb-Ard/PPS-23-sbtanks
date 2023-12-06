package org.aas.sbtanks.entities.obstacles

import org.aas.sbtanks.Main.{INTERFACE_SCALE, windowSize}
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.obstacles.view.ObstacleView
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.obstacles.view.scalafx.JFXAnimatedObstacleView
import org.aas.sbtanks.lifecycle.scalafx.JFXGameBootstrapper
import scalafx.scene.layout.Pane
import scalafx.stage.Stage

final case class LevelObstacleController(model: LevelObstacle with PositionBehaviour, view: ObstacleView, positionMultiplier: Double) extends Steppable:
    model.positionChanged += moveView
    moveView(model.positionX, model.positionY)
    view match
        case a: JFXAnimatedObstacleView => a.startAnimation()
        case _ => ()

    override def step(delta: Double) = this

    private def moveView(x: Double, y: Double) =
        view.move(x * positionMultiplier, y * positionMultiplier)


object LevelObstacleController:
    def factory(positionMultiplier: Double)(context: EntityRepositoryContext[?, ?, ?], model: LevelObstacle with PositionBehaviour, view: ObstacleView) =
        LevelObstacleController(model, view, positionMultiplier)
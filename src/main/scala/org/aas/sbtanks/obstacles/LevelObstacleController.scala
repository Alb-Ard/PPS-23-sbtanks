package org.aas.sbtanks.obstacles

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.obstacles.view.ObstacleView
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.entities.repository.EntityRepositoryContext

final case class LevelObstacleController(model: LevelObstacle with PositionBehaviour, view: ObstacleView, positionMultiplier: Double) extends Steppable:
    model.positionChanged += moveView
    moveView(model.positionX, model.positionY)

    override def step(delta: Double) = this

    private def moveView(x: Double, y: Double) =
        view.move(x * positionMultiplier, y * positionMultiplier)


object LevelObstacleController:
    def factory[VC](positionMultiplier: Double)(context: EntityRepositoryContext[VC], model: LevelObstacle with PositionBehaviour, view: ObstacleView) =
        LevelObstacleController(model, view, positionMultiplier)
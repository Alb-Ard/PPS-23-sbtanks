package org.aas.sbtanks.obstacles

import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour

case class LevelObstacle(val imagesPath: Seq[String])

object LevelObstacle:
    sealed trait LevelObstacleProvider(val imagesPath: String*):
        def apply(x: Double, y: Double) = 
            new LevelObstacle(imagesPath) with PositionBehaviour(x, y)

    sealed trait CollidableLevelObstacle(sizeX: Double, sizeY: Double, layer: CollisionLayer):
        this: LevelObstacleProvider =>

        override def apply(x: Double, y: Double) =
            new LevelObstacle(imagesPath) with PositionBehaviour(x, y) with CollisionBehaviour(sizeX, sizeY, layer, Seq.empty)

    case object BrickWall extends LevelObstacleProvider("obstacles/brick_wall.png") with CollidableLevelObstacle(1, 1, CollisionLayer.WallsLayer)
    case object SteelWall extends LevelObstacleProvider("obstacles/steel_wall.png") with CollidableLevelObstacle(1, 1, CollisionLayer.WallsLayer)
    case object Trees extends LevelObstacleProvider("obstacles/trees.png")
    case object Water extends LevelObstacleProvider("obstacles/water_1.png", "obstacles/water_2.png") with CollidableLevelObstacle(1, 1, CollisionLayer.NonWalkableLayer)